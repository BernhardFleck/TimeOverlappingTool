package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.service.TimetableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/timetable")
class TimetableController(@Autowired val timetableService: TimetableService) {

    @GetMapping("/showNextTwoWeeks")
    fun showTimetableForNextTwoWeeksInView(): ModelAndView {
        var modelAndView = ModelAndView()
        val datesOfNextTwoWeeks = timetableService.getListOfNextTwoWeeks()

        modelAndView.viewName = "timetable"
        modelAndView.addObject("dates", datesOfNextTwoWeeks)

        return modelAndView
    }


}