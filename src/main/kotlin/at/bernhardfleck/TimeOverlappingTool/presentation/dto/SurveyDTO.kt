package at.bernhardfleck.TimeOverlappingTool.presentation.dto

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.SelectedDay
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

class SurveyDTO {
    var purpose: String = ""

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    lateinit var startDate: LocalDate

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    lateinit var endDate: LocalDate

    var minimumParticipants = 2

    var participant: Participant = Participant()

    var selectedDays = mutableListOf<SelectedDayDTO>()

    constructor()

    constructor(
        purpose: String,
        startDate: LocalDate,
        endDate: LocalDate,
        minimumParticipantsForMatch: Int,
        participant: Participant,
        selectedDays: MutableList<SelectedDayDTO>
    ) {
        this.purpose = purpose
        this.startDate = startDate
        this.endDate = endDate
        this.minimumParticipants = minimumParticipantsForMatch
        this.participant = participant
        this.selectedDays = selectedDays
    }
}