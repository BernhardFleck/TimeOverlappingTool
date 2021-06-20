package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.presentation.dto.DtoToEntityConverter.mapper.convert
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

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
    fun submitSurvey(@ModelAttribute surveyDTO: SurveyDTO): String {
        val resultViewName = "result"
        val survey = convert(surveyDTO)

        surveyService.saveAfterValidationOf(survey)

        return resultViewName
    }

}