package at.bernhardfleck.TimeOverlappingTool.presentation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView
import java.util.*

@Controller
@RequestMapping("/")
class IndexController {

    @GetMapping
    fun showIndexPage(): String {
        return "index"
    }

    @GetMapping("/selectView")
    fun showResultsOrParticipationForm(
        @RequestParam surveyId: UUID,
        @RequestParam("selectionBox", required = false)
        resultIsSelected: Boolean,
    ): RedirectView {
        val resultFormUrl = "/survey/result/$surveyId"
        val participateFormUrl = "/survey/participate/$surveyId"

        return if (resultIsSelected) RedirectView(resultFormUrl) else RedirectView(participateFormUrl)
    }
}