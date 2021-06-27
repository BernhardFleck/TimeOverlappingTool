package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@ActiveProfiles("test")
@SpringBootTest
class ResultServiceTest(@Autowired val resultService: ResultService) {

    @Test
    fun `ensure datesMappedToParticipants contains all dates from start to end of the survey`() {
        val john = Participant("John", "Doe")
        val jane = Participant("Jane", "Doe")
        val johnsSelectedDates = listOf(
            LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20),
            LocalDate.of(2021, 6, 25), LocalDate.of(2021, 6, 29)
        )
        val janesSelectedDates = listOf(
            LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 21),
            LocalDate.of(2021, 6, 25), LocalDate.of(2021, 7, 2)
        )
        val participants = mutableListOf(john, jane)
        val submissions = mutableListOf(
            Submission(john, johnsSelectedDates),
            Submission(jane, janesSelectedDates)
        )
        val survey = Survey(
            purpose = "SoccerNight",
            minimumParticipants = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participants = participants,
            submissions = submissions
        )
        val map: Map<LocalDate, MutableList<Participant>>

        map = resultService.getDatesMappedToParticipantsOf(survey)

        assertThat(map.keys).hasSize(14)
        assertThat(map.keys).contains(
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
    fun `ensure datesMappedToParticipants does not contain dates outside the range of the survey`() {
        val john = Participant("John", "Doe")
        val jane = Participant("Jane", "Doe")
        val johnsSelectedDates = listOf(
            LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20),
            LocalDate.of(2021, 6, 25), LocalDate.of(2021, 6, 29)
        )
        val janesSelectedDates = listOf(
            LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 21),
            LocalDate.of(2021, 6, 25), LocalDate.of(2021, 7, 2)
        )
        val participants = mutableListOf(john, jane)
        val submissions = mutableListOf(
            Submission(john, johnsSelectedDates),
            Submission(jane, janesSelectedDates)
        )
        val survey = Survey(
            purpose = "SoccerNight",
            minimumParticipants = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participants = participants,
            submissions = submissions
        )
        val map: Map<LocalDate, MutableList<Participant>>

        map = resultService.getDatesMappedToParticipantsOf(survey)

        assertThat(map.keys).doesNotContain(
            LocalDate.of(2021, 6, 18),
            LocalDate.of(2021, 7, 3),
        )
    }

    @Test
    fun `ensure dates contain participants who actually chose the it`() {
        val john = Participant("John", "Doe")
        val jane = Participant("Jane", "Doe")
        val johnsSelectedDates = listOf(
            LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20),
            LocalDate.of(2021, 6, 25), LocalDate.of(2021, 6, 29)
        )
        val janesSelectedDates = listOf(
            LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 21),
            LocalDate.of(2021, 6, 25), LocalDate.of(2021, 7, 2)
        )
        val participants = mutableListOf(john, jane)
        val submissions = mutableListOf(
            Submission(john, johnsSelectedDates),
            Submission(jane, janesSelectedDates)
        )
        val survey = Survey(
            purpose = "SoccerNight",
            minimumParticipants = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participants = participants,
            submissions = submissions
        )
        val map: Map<LocalDate, MutableList<Participant>>

        map = resultService.getDatesMappedToParticipantsOf(survey)

        assertThat(map.get(LocalDate.of(2021, 6, 19))).contains(jane, john)
        assertThat(map.get(LocalDate.of(2021, 6, 20))).contains(john)
        assertThat(map.get(LocalDate.of(2021, 6, 21))).contains(jane)
        assertThat(map.get(LocalDate.of(2021, 6, 22))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 6, 23))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 6, 24))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 6, 25))).contains(jane, john)
        assertThat(map.get(LocalDate.of(2021, 6, 26))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 6, 27))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 6, 28))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 6, 29))).contains(john)
        assertThat(map.get(LocalDate.of(2021, 6, 30))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 7, 1))).isEmpty()
        assertThat(map.get(LocalDate.of(2021, 7, 2))).contains(jane)
    }

}