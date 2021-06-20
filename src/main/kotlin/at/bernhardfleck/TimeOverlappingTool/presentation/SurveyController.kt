package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.presentation.dto.DtoToEntityConverter.mapper.convert
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("/survey")
class SurveyController(@Autowired val surveyService: SurveyService) {

    @GetMapping("/create")
    fun showSurveyInView(): ModelAndView {
        val modelAndView = ModelAndView()
        val surveyDTO = SurveyDTO()
        val datesOfNextTwoWeeks = surveyService.getListOfNextTwoWeeks()

        surveyDTO.selectedDates = datesOfNextTwoWeeks
        modelAndView.viewName = "survey"
        modelAndView.addObject("dto", surveyDTO)

        return modelAndView
    }

    @PostMapping("/submit")
    fun submitSurvey(@ModelAttribute surveyDTO: SurveyDTO): ModelAndView {
        //TODO add bindingresult check and what about returning responseEntities?
        val modelAndView = ModelAndView()
        var survey = convert(surveyDTO)
        survey = surveyService.saveAfterValidationOf(survey)

        modelAndView.viewName = "shareSurvey"
        modelAndView.addObject("surveyId", survey.id)

        return modelAndView
    }

    @GetMapping("/participate/{surveyId}")
    @ResponseBody
    fun participateSurvey(@PathVariable surveyId: UUID): String {
        return surveyId.toString()
    }

}