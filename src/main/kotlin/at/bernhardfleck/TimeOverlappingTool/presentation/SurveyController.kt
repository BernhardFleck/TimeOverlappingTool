package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.presentation.dto.DtoToEntityConverter.Companion.convert
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import at.bernhardfleck.TimeOverlappingTool.presentation.helper.DatesCreator.Companion.getNextTwoWeeks
import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import java.util.*

@Controller
@RequestMapping("/survey")
class SurveyController(@Autowired val service: SurveyService) {

    @GetMapping("/create")
    fun showSurveyInView(): ModelAndView {
        val modelAndView = ModelAndView()
        val surveyDTO = SurveyDTO()
        val datesOfNextTwoWeeks = getNextTwoWeeks()

        surveyDTO.selectedDates = datesOfNextTwoWeeks
        modelAndView.viewName = "survey"
        modelAndView.addObject("dto", surveyDTO)
        modelAndView.addObject("intention", "createSurvey")
        return modelAndView
    }

    //TODO add bindingresult check and what about returning responseEntities?
    @PostMapping("/create")
    fun createSurvey(@ModelAttribute surveyDTO: SurveyDTO): ModelAndView {
        val modelAndView = ModelAndView()
        val submission = service.getSubmissionFrom(surveyDTO)
        var survey = convert(surveyDTO)

        survey = service.participation(survey, submission)
        survey.validate()
        survey = service.save(survey)

        modelAndView.viewName = "shareSurvey"
        modelAndView.addObject("surveyId", survey.id)
        return modelAndView
    }

    @GetMapping("/participate/{surveyId}")
    @ResponseBody
    fun showSurveyWithDetailsInView(@PathVariable surveyId: UUID): ModelAndView {
        val modelAndView = ModelAndView()
        val survey = service.getSurveyBy(surveyId)
        val surveyDTO = convert(survey)

        modelAndView.viewName = "survey"
        modelAndView.addObject("dto", surveyDTO)
        modelAndView.addObject("intention", "participateSurvey")
        return modelAndView
    }

    @PostMapping("/participate/{surveyId}")
    @ResponseBody
    fun participateInSurvey(
        @ModelAttribute surveyDTO: SurveyDTO,
        @PathVariable surveyId: String
    ): RedirectView {
        val surveyId = UUID.fromString(surveyId)
        val submission = service.getSubmissionFrom(surveyDTO)
        val survey = service.getSurveyBy(surveyId)
        val resultPageUrl = "/survey/result/$surveyId"

        submission.validate()
        service.participation(survey, submission)

        return RedirectView(resultPageUrl)
    }

}