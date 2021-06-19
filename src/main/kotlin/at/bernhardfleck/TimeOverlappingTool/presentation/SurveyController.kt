package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/survey")
class SurveyController(@Autowired val surveyService: SurveyService) {

    @GetMapping("/show")
    fun showSurveyInView(): ModelAndView {
        var modelAndView = ModelAndView()
        val datesOfNextTwoWeeks = surveyService.getListOfNextTwoWeeks()

        modelAndView.viewName = "survey"
        modelAndView.addObject("dates", datesOfNextTwoWeeks)

        return modelAndView
    }


}