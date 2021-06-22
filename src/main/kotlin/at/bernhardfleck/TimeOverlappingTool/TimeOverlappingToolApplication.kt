package at.bernhardfleck.TimeOverlappingTool

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.presentation.SurveyController
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate
import java.util.*


@SpringBootApplication
class TimeOverlappingToolApplication(@Autowired val surveyController: SurveyController) : CommandLineRunner {

    //TODO Delete init data
    override fun run(vararg args: String?) {
        val dtoForCreation = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("Jane", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 19), LocalDate.of(2021, 6, 20), LocalDate.of(2021, 6, 21))
        )
        val dtoForParticipation = SurveyDTO(
            purpose = "SoccerNight",
            minimumParticipantsForMatch = 5,
            startDate = LocalDate.of(2021, 6, 19),
            endDate = LocalDate.of(2021, 7, 2),
            participant = Participant("John", "Doe"),
            selectedDates = listOf(LocalDate.of(2021, 6, 20), LocalDate.of(2021, 6, 21), LocalDate.of(2021, 6, 25))
        )

        val modelAndView = surveyController.createSurvey(dtoForCreation)
        val surveyId = modelAndView.model.get("surveyId") as UUID
        println()
        println("http://localhost:8080/survey/result/" + surveyId)
        println()
        surveyController.participateInSurvey(dtoForParticipation, surveyId.toString())

    }
}

fun main(args: Array<String>) {
    runApplication<TimeOverlappingToolApplication>(*args)
}
