package at.bernhardfleck.TimeOverlappingTool.presentation.helper

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.stream.Collectors
import java.util.stream.Collectors.toList

class DatesCreator {

    companion object {
        fun getNextTwoWeeks(): List<LocalDate> {
            val tomorrow = now().plusDays(1)
            val fourteenDaysLater = tomorrow.plusDays(14)

            return tomorrow
                .datesUntil(fourteenDaysLater)
                .collect(Collectors.toList())
        }

        fun datesFromStartToEndOf(survey: Survey): List<LocalDate> {
            val start = survey.startDate
            val end = survey.endDate
            val endInclusive = end.plusDays(1)

            return start.datesUntil(endInclusive)
                .collect(toList())
        }

        fun emptyParticipantsMappedToDatesOf(survey: Survey): Map<LocalDate, MutableList<Participant>> {
            return datesFromStartToEndOf(survey)
                .map { it to mutableListOf<Participant>() }
                .toMap()
        }
    }
}