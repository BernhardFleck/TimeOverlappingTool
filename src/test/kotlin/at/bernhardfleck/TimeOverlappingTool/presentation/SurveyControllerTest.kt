package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SelectedDayDTO
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
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
        val passedDates: List<SelectedDayDTO>

        passedModelAndView = surveyController.showSurveyCreationPage()
        passedDTO = passedModelAndView.model.get("dto") as SurveyDTO
        passedDates = passedDTO.selectedDays

        assertThat(passedDates).isNotEmpty
    }

    @Test
    fun `ensure that the list of dates passed to the view contains 14 dates`() {
        val passedModelAndView: ModelAndView
        val passedDTO: SurveyDTO
        val passedDates: List<SelectedDayDTO>

        passedModelAndView = surveyController.showSurveyCreationPage()
        passedDTO = passedModelAndView.model.get("dto") as SurveyDTO
        passedDates = passedDTO.selectedDays

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
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20))
            )
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
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20))
            )
        )
        var modelAndView = surveyController.createSurvey(surveyDTO)
        val surveyId = modelAndView.model.get("surveyId") as UUID

        modelAndView = surveyController.showParticipationPageOf(surveyId)
        surveyDTO = modelAndView.model.get("dto") as SurveyDTO

        assertThat(surveyDTO.purpose).isEqualTo("SoccerNight")
        assertThat(surveyDTO.minimumParticipants).isEqualTo(5)
    }
    
    @Test
    fun `ensure that the lowest date to select of a shared survey is the start date`() {
        var surveyDTO = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20))
            )
        )
        var modelAndView = surveyController.createSurvey(surveyDTO)
        val surveyId = modelAndView.model.get("surveyId") as UUID
        val selectedDates: List<LocalDate>

        modelAndView = surveyController.showParticipationPageOf(surveyId)
        surveyDTO = modelAndView.model.get("dto") as SurveyDTO
        selectedDates = surveyDTO.selectedDays.map { it.date!! }

        assertThat(selectedDates.minOrNull()).isEqualTo(LocalDate.of(2021, 6, 19))
    }

    @Test
    fun `ensure that the last date to select of a shared survey is the end date`() {
        var surveyDTO = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20))
            )
        )
        var modelAndView = surveyController.createSurvey(surveyDTO)
        val surveyId = modelAndView.model.get("surveyId") as UUID
        val selectedDates: List<LocalDate>

        modelAndView = surveyController.showParticipationPageOf(surveyId)
        surveyDTO = modelAndView.model.get("dto") as SurveyDTO
        selectedDates = surveyDTO.selectedDays.map { it.date!! }

        assertThat(selectedDates.maxOrNull()).isEqualTo(LocalDate.of(2021, 7, 2))
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
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20))
            )
        )
        val dtoFromParticipant = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("Jane", "Doe"),
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20))
            )
        )
        val modelAndView = surveyController.createSurvey(dtoFromCreator)
        val surveyId = modelAndView.model.get("surveyId") as UUID
        val surveyIdString = surveyId.toString()
        val redirectView: ModelAndView

        redirectView = surveyController.participateInSurvey(dtoFromParticipant, surveyIdString)
        expectedForwardingPage = redirectView.viewName!!

        assertThat(expectedForwardingPage).contains("result")
    }

}