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
        val surveyDTO = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        )

        modelAndView = surveyController.createSurvey(surveyDTO)
        expectedSurveyId = modelAndView.model.get("surveyId") as UUID

        assertThat(expectedSurveyId).isNotNull
    }

    @Test
    fun `ensure sharing a participation link returns a modelAndView containing the surveys purpose and minParticipantsToMatch`() {
        var surveyDTO = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        )
        var modelAndView = surveyController.createSurvey(surveyDTO)
        val surveyId = modelAndView.model.get("surveyId") as UUID

        modelAndView = surveyController.showSurveyWithDetailsInView(surveyId)
        surveyDTO = modelAndView.model.get("dto") as SurveyDTO

        assertThat(surveyDTO.purpose).isEqualTo("SoccerNight")
        assertThat(surveyDTO.minimumParticipantsForMatch).isEqualTo(5)
    }

    @Test
    fun `ensure sharing a participation link returns the dates from start to end of the survey`() {
        var surveyDTO = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        )
        var modelAndView = surveyController.createSurvey(surveyDTO)
        val surveyId = modelAndView.model.get("surveyId") as UUID

        modelAndView = surveyController.showSurveyWithDetailsInView(surveyId)
        surveyDTO = modelAndView.model.get("dto") as SurveyDTO

        assertThat(surveyDTO.selectedDates).contains(
            LocalDate.of(2021, 6, 19),
            LocalDate.of(2021, 6, 20),
            LocalDate.of(2021, 6, 21),
            LocalDate.of(2021, 6, 22),
            LocalDate.of(2021, 6, 23),
            LocalDate.of(2021, 6, 24),
            LocalDate.of(2021, 6, 25),
            LocalDate.of(2021, 6, 26),
            LocalDate.of(2021, 6, 27),
            LocalDate.of(2021, 6, 28),
            LocalDate.of(2021, 6, 29),
            LocalDate.of(2021, 6, 30),
            LocalDate.of(2021, 7, 1),
            LocalDate.of(2021, 7, 2),
        )
    }

    @Test
    fun `ensure that the lowest date to select of a shared survey is the start date`() {
        var surveyDTO = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        )
        var modelAndView = surveyController.createSurvey(surveyDTO)
        val surveyId = modelAndView.model.get("surveyId") as UUID

        modelAndView = surveyController.showSurveyWithDetailsInView(surveyId)
        surveyDTO = modelAndView.model.get("dto") as SurveyDTO

        assertThat(surveyDTO.selectedDates.minOrNull()).isEqualTo(LocalDate.of(2021, 6, 19))
    }

    @Test
    fun `ensure that the last date to select of a shared survey is the end date`() {
        var surveyDTO = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        )
        var modelAndView = surveyController.createSurvey(surveyDTO)
        val surveyId = modelAndView.model.get("surveyId") as UUID

        modelAndView = surveyController.showSurveyWithDetailsInView(surveyId)
        surveyDTO = modelAndView.model.get("dto") as SurveyDTO

        assertThat(surveyDTO.selectedDates.maxOrNull()).isEqualTo(LocalDate.of(2021, 7, 2))
    }

    @Test
    fun `ensure participating in a survey successfully, redirects to the result page`() {
        val expectedForwardingPage: String
        val dtoFromCreator = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        )
        val dtoFromParticipant = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("Jane", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20))
        )
        var modelAndView = surveyController.createSurvey(dtoFromCreator)
        val surveyId = modelAndView.model.get("surveyId") as UUID
        val surveyIdString = surveyId.toString()

        modelAndView = surveyController.participateInSurvey(dtoFromParticipant, surveyIdString)
        expectedForwardingPage = modelAndView.viewName!!

        assertThat(expectedForwardingPage).isEqualTo("result")
    }

}