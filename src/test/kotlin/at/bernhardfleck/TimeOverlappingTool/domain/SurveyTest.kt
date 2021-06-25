package at.bernhardfleck.TimeOverlappingTool.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@ActiveProfiles("test")
@SpringBootTest
class SurveyTest {

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

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            survey.validate()
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

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            survey.validate()
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

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            survey.validate()
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

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            survey.validate()
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

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            survey.validate()
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

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            survey.validate()
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

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            survey.validate()
        }
    }
}