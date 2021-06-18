package at.bernhardfleck.TimeOverlappingTool.presentation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate

@SpringBootTest
class TimetableControllerTest(@Autowired val timetableController: TimetableController) {

    @Test
    fun `ensure that dependency injection works with timetableController`() {
        assertThat(timetableController).isNotNull
    }

    @Test
    fun `ensure that the list of dates passed to the view is not empty`() {
        var passedModelAndView: ModelAndView
        val passedDates: List<LocalDate>

        passedModelAndView = timetableController.showTimetableForNextTwoWeeksInView()
        passedDates = passedModelAndView.model.get("dates") as List<LocalDate>

        assertThat(passedDates).isNotEmpty
    }

    @Test
    fun `ensure that the list of dates passed to the view contains 14 dates`() {
        var passedModelAndView: ModelAndView
        val passedDates: List<LocalDate>

        passedModelAndView = timetableController.showTimetableForNextTwoWeeksInView()
        passedDates = passedModelAndView.model.get("dates") as List<LocalDate>

        assertThat(passedDates).hasSize(14)
    }
}