package at.bernhardfleck.TimeOverlappingTool.presentation.helper

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SelectedDayDTO
import java.time.LocalDate
import java.time.LocalDate.now
import kotlin.streams.toList

class DatesCreator {

    companion object {
        fun getNextTwoWeeks(): MutableList<SelectedDayDTO> {
            val tomorrow = now().plusDays(1)
            val fourteenDaysLater = tomorrow.plusDays(14)
            val nextTwoWeekDates = tomorrow
                .datesUntil(fourteenDaysLater)
                .toList()

            return nextTwoWeekDates
                .map { SelectedDayDTO(it, "") }
                .toMutableList()
        }

        fun emptyParticipantsAndNotesMappedToDaysOf(survey: Survey): Map<LocalDate, MutableMap<Participant, String>> {
            return daysFromStartToEndOf(survey)
                .map { it to mutableMapOf<Participant, String>() }
                .toMap()
        }

        fun daysFromStartToEndOf(survey: Survey): List<LocalDate> {
            val start = survey.startDate
            val end = survey.endDate
            val endInclusive = end.plusDays(1)

            return start.datesUntil(endInclusive).toList()
        }

        fun getDaysInRangeOf(survey: Survey): MutableList<SelectedDayDTO> =
            daysFromStartToEndOf(survey)
                .map { SelectedDayDTO(note = "", date = it) }
                .toMutableList()
    }
}