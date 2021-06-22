package at.bernhardfleck.TimeOverlappingTool.presentation.helper

import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import java.time.LocalDate
import java.util.stream.Collectors

class DatesCreator {

    companion object {
        fun getNextTwoWeeks(): List<LocalDate> {
            val tomorrow = LocalDate.now().plusDays(1)
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
                .collect(Collectors.toList())
        }
    }
}