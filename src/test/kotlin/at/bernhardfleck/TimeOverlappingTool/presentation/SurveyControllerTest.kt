package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate
import java.util.*

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

    @Test
    fun `ensure saving a survey returns a modelAndView containing a surveyId `() {
        val modelAndView: ModelAndView
        val expectedSurveyId: UUID
        val surveyDTO = SurveyDTO()
        surveyDTO.purpose = "SoccerNight"
        surveyDTO.minimumParticipantsForMatch = 5
        surveyDTO.startDate = LocalDate.of(2021, 6, 19)
        surveyDTO.endDate = LocalDate.of(2021, 7, 3)
        surveyDTO.selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        surveyDTO.participant = Participant("John", "Doe")

        modelAndView = surveyController.submitSurvey(surveyDTO)
        expectedSurveyId = modelAndView.model.get("surveyId") as UUID

        assertThat(expectedSurveyId).isNotNull
    }

}