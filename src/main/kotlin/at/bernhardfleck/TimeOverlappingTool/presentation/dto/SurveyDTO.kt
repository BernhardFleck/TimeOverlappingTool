package at.bernhardfleck.TimeOverlappingTool.presentation.dto

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

class SurveyDTO {
    var purpose: String = ""

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var startDate: LocalDate? = null

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var endDate: LocalDate? = null

    var minimumParticipantsForMatch = 2

    var creator: Participant = Participant()

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var selectedDates: List<LocalDate> = emptyList()

}