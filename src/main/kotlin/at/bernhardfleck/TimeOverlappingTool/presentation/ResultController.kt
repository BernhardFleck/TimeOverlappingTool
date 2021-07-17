package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.service.ResultService
import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class ResultController(
    @Autowired val surveyService: SurveyService,
    @Autowired val resultService: ResultService,
    @Autowired val indexController: IndexController,
) : BaseController() {

    @GetMapping("/survey/result/{surveyId}")
    fun showResultView(@PathVariable surveyId: String): ModelAndView {
        return try {
            showResultViewOf(surveyId)
        } catch (exception: IllegalArgumentException) {
            addErrorMessageToPage(exception, indexController.showIndexPage())
        }
    }

    private fun showResultViewOf(surveyId: String): ModelAndView {
        val modelAndView = ModelAndView()
        val surveyId = UUID.fromString(surveyId)
        val survey = surveyService.getSurveyBy(surveyId)
        val datesMappedToParticipants = resultService.getDatesMappedToParticipantsOf(survey)

        modelAndView.viewName = "result"
        modelAndView.addObject("survey", survey)
        modelAndView.addObject("allDates", datesMappedToParticipants)
        return modelAndView
    }

}