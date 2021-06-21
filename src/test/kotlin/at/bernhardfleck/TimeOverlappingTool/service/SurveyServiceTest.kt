package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDate.now


@SpringBootTest
class SurveyServiceTest(@Autowired val surveyService: SurveyService) {

    @Test
    fun `ensure list of the next two weeks starts with tomorrow`() {
        val now = now()
        val tomorrow = now.plusDays(1)
        val datesOfNextTwoWeeks: List<LocalDate>
        val firstDate: LocalDate

        datesOfNextTwoWeeks = surveyService.getNextTwoWeeks()
        firstDate = datesOfNextTwoWeeks.get(0)

        assertThat(firstDate).isEqualTo(tomorrow)
    }

    @Test
    fun `ensure list of the next two weeks ends with today plus 14 days`() {
        val tomorrow = now().plusDays(1)
        val expectedEnd = tomorrow.plusDays(13) // because 14th day is exlusive
        val datesOfNextTwoWeeks: List<LocalDate>
        val lastDate: LocalDate
        val lastIndex: Int

        datesOfNextTwoWeeks = surveyService.getNextTwoWeeks()
        lastIndex = datesOfNextTwoWeeks.size - 1
        lastDate = datesOfNextTwoWeeks.get(lastIndex)

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
            LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20),
            LocalDate.of(2021, 6, 25), LocalDate.of(2021, 6, 29)
        )
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        var survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        survey = surveyService.saveAfterValidationOf(survey)

        assertThat(survey.id).isNotNull
        assertThat(survey.participants).hasSizeGreaterThan(0)
        assertThat(survey.participants).contains(creator)
    }

    @Test
    fun `ensure that a submitted survey consisting of blank names of the creator throws an exception`() {
        val firstName = ""
        val lastName = "  "
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = 5
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = listOf(LocalDate.of(2021, 6, 20))
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        val survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        assertThrows(IllegalArgumentException::class.java) {
            surveyService.saveAfterValidationOf(survey)
        }
    }

    @Test
    fun `ensure that a submitted survey consisting of a blank purpose throws an exception`() {
        val firstName = "John"
        val lastName = "Doe"
        val purpose = "  "
        val minimumParticipantsForMatch = 5
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = listOf(LocalDate.of(2021, 6, 20))
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        val survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        assertThrows(IllegalArgumentException::class.java) {
            surveyService.saveAfterValidationOf(survey)
        }
    }

    @Test
    fun `given a minimum of participants for a match of minus one throws an exception`() {
        val firstName = "John"
        val lastName = "Doe"
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = -1
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = listOf(LocalDate.of(2021, 6, 20))
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        val survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        assertThrows(IllegalArgumentException::class.java) {
            surveyService.saveAfterValidationOf(survey)
        }
    }

    @Test
    fun `given a minimum of participants for a match of zero throws an exception`() {
        val firstName = "John"
        val lastName = "Doe"
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = 0
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = listOf(LocalDate.of(2021, 6, 20))
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        val survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        assertThrows(IllegalArgumentException::class.java) {
            surveyService.saveAfterValidationOf(survey)
        }
    }

    @Test
    fun `given a minimum of participants for a match of plus one throws an exception`() {
        val firstName = "John"
        val lastName = "Doe"
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = 1
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = listOf(LocalDate.of(2021, 6, 20))
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        val survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        assertThrows(IllegalArgumentException::class.java) {
            surveyService.saveAfterValidationOf(survey)
        }
    }

    @Test
    fun `ensure that a submitted survey with no valid participants throws an exception`() {
        val purpose = "SoccerNight"
        val minimumParticipantsForMatch = 5
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = listOf(LocalDate.of(2021, 6, 20))
        val participants = mutableListOf<Participant>()
        val submission = Submission(Participant(), selectedDates)
        val submissions = mutableListOf(submission)
        val survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        assertThrows(IllegalArgumentException::class.java) {
            surveyService.saveAfterValidationOf(survey)
        }
    }

    @Test
    fun `ensure that a submitted survey with no selected dates throws an exception`() {
        val firstName = "John"
        val lastName = "Doe"
        val purpose = "WatchingSoccerTogether"
        val minimumParticipantsForMatch = 2
        val startDate = LocalDate.of(2021, 6, 19)
        val endDate = LocalDate.of(2021, 7, 3)
        val selectedDates = emptyList<LocalDate>()
        val creator = Participant(firstName, lastName)
        val participants = mutableListOf(creator)
        val submission = Submission(creator, selectedDates)
        val submissions = mutableListOf(submission)
        val survey = Survey(purpose, startDate, endDate, minimumParticipantsForMatch, participants, submissions)

        assertThrows(IllegalArgumentException::class.java) {
            surveyService.saveAfterValidationOf(survey)
        }
    }
}