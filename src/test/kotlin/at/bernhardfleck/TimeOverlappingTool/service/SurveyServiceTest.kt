package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.SelectedDay
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SelectedDayDTO
import at.bernhardfleck.TimeOverlappingTool.presentation.helper.DatesCreator.Companion.getNextTwoWeeks
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.time.LocalDate.now

@ActiveProfiles("test")
@SpringBootTest
class SurveyServiceTest(@Autowired val surveyService: SurveyService) {

    @Test
    fun `ensure list of the next two weeks starts with tomorrow`() {
        val now = now()
        val tomorrow = now.plusDays(1)
        val datesOfNextTwoWeeks: List<SelectedDayDTO>
        val firstDay: LocalDate

        datesOfNextTwoWeeks = getNextTwoWeeks()
        firstDay = datesOfNextTwoWeeks.get(0).date!!

        assertThat(firstDay).isEqualTo(tomorrow)
    }

    @Test
    fun `ensure list of the next two weeks ends with today plus 14 days`() {
        val tomorrow = now().plusDays(1)
        val expectedEnd = tomorrow.plusDays(13) // because 14th day is exlusive
        val datesOfNextTwoWeeks: List<SelectedDayDTO>
        val lastDate: LocalDate
        val lastIndex: Int

        datesOfNextTwoWeeks = getNextTwoWeeks()
        lastIndex = datesOfNextTwoWeeks.size - 1
        lastDate = datesOfNextTwoWeeks.get(lastIndex).date!!

        assertThat(lastDate).isEqualTo(expectedEnd)
    }

    @Test
    fun `ensure that a survey can be saved so that the creator is already part of the participants`() {
        val firstName = "John"
        val lastName = "Doe"
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = 5
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = listOf(
            SelectedDay(note = "", LocalDate.of(2021, 6, 19)),
            SelectedDay(note = "", LocalDate.of(2021, 6, 20)),
            SelectedDay(note = "", LocalDate.of(2021, 6, 25)),
            SelectedDay(note = "", LocalDate.of(2021, 6, 29))
        )
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        var survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        survey.validate()
        submission.validate()
        survey = surveyService.save(survey)

        assertThat(survey.id).isNotNull
        assertThat(survey.participants).hasSizeGreaterThan(0)
        assertThat(survey.participants).contains(creator)
    }

    @Test
    fun `ensure that participating in a survey adds a new participant`() {
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = 5
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val creator = Participant("John", "Doe")
        val creatorsSelectedDates = listOf(
            SelectedDay(note = "", LocalDate.of(2021, 6, 19)),
            SelectedDay(note = "", LocalDate.of(2021, 6, 20))
        )
        val creatorsSubmission = Submission(creator, creatorsSelectedDates)
        val participants = mutableListOf(creator)
        val submissions = mutableListOf(creatorsSubmission)
        var survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)
        val participant = Participant("Jane", "Doe")
        val participantsSelectedDates = listOf(
            SelectedDay(note = "", LocalDate.of(2021, 6, 20)),
            SelectedDay(note = "", LocalDate.of(2021, 6, 21))
        )
        val participantsSubmission = Submission(participant, participantsSelectedDates)

        survey.validate()
        creatorsSubmission.validate()
        participantsSubmission.validate()
        survey = surveyService.save(survey)
        surveyService.participation(survey, participantsSubmission)

        assertThat(survey.submissions).hasSize(2)
    }

    @Test
    fun `ensure that participating in a survey adds a new submission`() {
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = 5
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val creator = Participant("John", "Doe")
        val creatorsSelectedDates = listOf(
            SelectedDay(note = "", LocalDate.of(2021, 6, 19)),
            SelectedDay(note = "", LocalDate.of(2021, 6, 20))
        )
        val creatorsSubmission = Submission(creator, creatorsSelectedDates)
        val participants = mutableListOf(creator)
        val submissions = mutableListOf(creatorsSubmission)
        var survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)
        val participant = Participant("Jane", "Doe")
        val participantsSelectedDates = listOf(
            SelectedDay(note = "", LocalDate.of(2021, 6, 20)),
            SelectedDay(note = "", LocalDate.of(2021, 6, 21))
        )

        val participantsSubmission = Submission(participant, participantsSelectedDates)

        survey.validate()
        creatorsSubmission.validate()
        participantsSubmission.validate()
        survey = surveyService.save(survey)
        surveyService.participation(survey, participantsSubmission)

        assertThat(survey.submissions).hasSize(2)
    }

}