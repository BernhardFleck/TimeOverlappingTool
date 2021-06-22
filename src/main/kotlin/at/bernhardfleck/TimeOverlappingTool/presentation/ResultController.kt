package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class ResultController(@Autowired val surveyService: SurveyService) {

    @GetMapping("/survey/result/{surveyId}")
    fun showResultViewBy(@PathVariable surveyId: String): ModelAndView {
        val surveyId = UUID.fromString(surveyId)
        val modelAndView = ModelAndView()
        val survey = surveyService.getSurveyBy(surveyId)


        modelAndView.viewName = "result"
        modelAndView.addObject(survey)
        return modelAndView
    }
}