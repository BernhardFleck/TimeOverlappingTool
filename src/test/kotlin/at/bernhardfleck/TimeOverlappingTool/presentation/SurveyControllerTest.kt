package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SelectedDayDTO
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate
import java.util.*
import org.hamcrest.MatcherAssert.assertThat as hamcrestAssertsThat

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
    fun `ensure sharing a participation link returns the dates from start to end of the survey`() {
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

        hamcrestAssertsThat(
            surveyDTO.selectedDays, containsInAnyOrder(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 21)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 22)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 23)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 24)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 25)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 26)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 27)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 28)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 29)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 30)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 7, 1)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 7, 1)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 7, 2))
            )
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