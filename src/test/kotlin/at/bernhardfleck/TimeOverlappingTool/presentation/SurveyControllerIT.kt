package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.SelectedDay
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.persistence.SurveyRepository
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
class SurveyControllerIT(
    @Autowired val surveyController: SurveyController,
    @Autowired val surveyRepository: SurveyRepository
) {

    @Test
    fun `ensure that a saved survey contains the creator of it`() {
        val modelAndView: ModelAndView
        val surveyId: UUID
        val survey: Survey
        val submission: Submission
        val creator: Participant
        val surveyDTO = SurveyDTO()
        surveyDTO.purpose = "SoccerNight"
        surveyDTO.minimumParticipants = 5
        surveyDTO.startDate = LocalDate.of(2021, 6, 19)
        surveyDTO.endDate = LocalDate.of(2021, 7, 3)
        surveyDTO.selectedDays = mutableListOf(
            SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
            SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20))
        )
        surveyDTO.participant = Participant("John", "Doe")

        modelAndView = surveyController.createSurvey(surveyDTO)
        surveyId = modelAndView.model.get("surveyId") as UUID
        survey = surveyRepository.getById(surveyId)
        submission = survey.submissions.first()
        creator = submission.participant

        assertThat(creator.firstName).isEqualTo("John")
        assertThat(creator.lastName).isEqualTo("Doe")
    }

    @Test
    fun `ensure that a saved survey contains the selected dates`() {
        val modelAndView: ModelAndView
        val surveyId: UUID
        val survey: Survey
        val submission: Submission
        val selectedDates: List<SelectedDay>
        val surveyDTO = SurveyDTO()
        surveyDTO.purpose = "SoccerNight"
        surveyDTO.minimumParticipants = 5
        surveyDTO.startDate = LocalDate.of(2021, 6, 19)
        surveyDTO.endDate = LocalDate.of(2021, 7, 3)
        surveyDTO.selectedDays = mutableListOf(
            SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
            SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20)),
        )
        surveyDTO.participant = Participant("John", "Doe")

        modelAndView = surveyController.createSurvey(surveyDTO)
        surveyId = modelAndView.model.get("surveyId") as UUID
        survey = surveyRepository.getById(surveyId)
        submission = survey.submissions.first()
        selectedDates = submission.selectedDays

        assertThat(selectedDates).contains(SelectedDay(note = "", LocalDate.of(2021, 6, 19)))
        assertThat(selectedDates).contains(SelectedDay(note = "", LocalDate.of(2021, 6, 20)))
    }

}