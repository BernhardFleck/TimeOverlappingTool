package at.bernhardfleck.TimeOverlappingTool.presentation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("/")
class IndexController : BaseController() {

    private val indexViewName = "index"

    @GetMapping
    fun showIndexPage(): ModelAndView {
        val modelAndView = ModelAndView()

        modelAndView.viewName = indexViewName

        return modelAndView
    }

    @GetMapping("/selectView")
    fun showResultsOrParticipationPage(
        @RequestParam surveyId: UUID,
        @RequestParam("selectionBox", required = false)
        resultIsSelected: Boolean,
    ): ModelAndView {
        return try {
            redirectionToSelectedView(surveyId, resultIsSelected)
        } catch (exception: Exception) {
            addErrorMessageToView(exception, showIndexPage())
        }
    }

    private fun redirectionToSelectedView(surveyId: UUID, resultIsSelected: Boolean): ModelAndView {
        return if (resultIsSelected) redirectToResultPageOf(surveyId)
        else redirectToParticipationPageOf(surveyId)
    }

    private fun redirectToResultPageOf(surveyId: UUID): ModelAndView {
        val resultPageUrl = "redirect:/survey/result/$surveyId"
        return ModelAndView(resultPageUrl)
    }

    private fun redirectToParticipationPageOf(surveyId: UUID): ModelAndView {
        val participationPageUrl = "redirect:/survey/participate/$surveyId"
        return ModelAndView(participationPageUrl)
    }
}