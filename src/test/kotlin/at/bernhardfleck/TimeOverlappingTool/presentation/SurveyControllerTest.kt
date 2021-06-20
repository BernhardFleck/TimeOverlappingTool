package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate

@SpringBootTest
class SurveyControllerTest(@Autowired val surveyController: SurveyController) {

    @Test
    fun `ensure that dependency injection works with timetableController`() {
        assertThat(surveyController).isNotNull
    }

    @Test
    fun `ensure that the list of dates passed to the view is not empty`() {
        val passedModelAndView: ModelAndView
        val passedDTO: SurveyDTO
        val passedDates: List<LocalDate>

        passedModelAndView = surveyController.showSurveyInView()
        passedDTO = passedModelAndView.model.get("dto") as SurveyDTO
        passedDates = passedDTO.selectedDates

        assertThat(passedDates).isNotEmpty
    }

    @Test
    fun `ensure that the list of dates passed to the view contains 14 dates`() {
        val passedModelAndView: ModelAndView
        val passedDTO: SurveyDTO
        val passedDates: List<LocalDate>

        passedModelAndView = surveyController.showSurveyInView()
        passedDTO = passedModelAndView.model.get("dto") as SurveyDTO
        passedDates = passedDTO.selectedDates

        assertThat(passedDates).hasSize(14)
    }

}