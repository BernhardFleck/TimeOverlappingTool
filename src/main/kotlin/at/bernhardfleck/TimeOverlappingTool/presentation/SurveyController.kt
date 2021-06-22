package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.presentation.dto.DtoToEntityConverter.Converter.convert
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("/survey")
class SurveyController(@Autowired val service: SurveyService) {

    @GetMapping("/create")
    fun showSurveyInView(): ModelAndView {
        val modelAndView = ModelAndView()
        val surveyDTO = SurveyDTO()
        val datesOfNextTwoWeeks = service.getNextTwoWeeks()

        surveyDTO.selectedDates = datesOfNextTwoWeeks
        modelAndView.viewName = "survey"
        modelAndView.addObject("dto", surveyDTO)
        modelAndView.addObject("intention", "createSurvey")

        return modelAndView
    }

    @PostMapping("/create")
    fun createSurvey(@ModelAttribute surveyDTO: SurveyDTO): ModelAndView {
        //TODO add bindingresult check and what about returning responseEntities?
        val modelAndView = ModelAndView()
        var survey = convert(surveyDTO)
        var submission = service.getSubmissionFrom(surveyDTO)
        var participant = service.getParticipantFrom(surveyDTO)

        service.add(submission, survey)
        service.add(participant, survey)
        survey = service.saveAfterValidationOf(survey)

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

    }

}