package at.bernhardfleck.TimeOverlappingTool

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.presentation.SurveyController
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SelectedDayDTO
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.*

@SpringBootApplication
class TimeOverlappingToolApplication(@Autowired val surveyController: SurveyController) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val tomorrowDate = now().plusDays(1)
        val fourteenDaysLaterDate = tomorrowDate.plusDays(14)
        val tomorrow = SelectedDayDTO(note = "Afternoon only", date = tomorrowDate)
        val fourteenDaysLater = SelectedDayDTO(note = "After 17:00", date = fourteenDaysLaterDate)

        val dtoForCreation = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 2,
            startDate = tomorrowDate,
            endDate = fourteenDaysLaterDate,
            participant = Participant("Jane", "Doe"),
            selectedDays = mutableListOf(tomorrow)
        )
        val dtoForParticipation = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 2,
            startDate = tomorrowDate,
            endDate = fourteenDaysLaterDate,
            participant = Participant("John", "Doe"),
            selectedDays = mutableListOf(tomorrow, fourteenDaysLater)
        )

        val modelAndView = surveyController.createSurvey(dtoForCreation)
        val surveyId = modelAndView.model.get("surveyId") as UUID
        println()
        println("http://localhost:8080/survey/result/" + surveyId)
        println()
        surveyController.participateInSurvey(dtoForParticipation, surveyId.toString())

        // Create finishing or finished surveys for trying out spring integration
        val finishingDto = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 2,
            startDate = now().minusDays(13),
            endDate = now(),
            participant = Participant("Jane", "Doe"),
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 21)),
            )
        )
        val finishedDto = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 2,
            startDate = now().minusDays(14),
            endDate = now().minusDays(1),
            participant = Participant("Jane", "Doe"),
            selectedDays = mutableListOf(
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 19)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 20)),
                SelectedDayDTO(note = "", date = LocalDate.of(2021, 6, 21)),
            )
        )
        surveyController.createSurvey(finishingDto)
        surveyController.createSurvey(finishedDto)

    }
}

fun main(args: Array<String>) {
    runApplication<TimeOverlappingToolApplication>(*args)
}
