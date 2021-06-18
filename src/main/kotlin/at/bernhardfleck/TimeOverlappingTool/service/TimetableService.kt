package at.bernhardfleck.TimeOverlappingTool.service

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.stream.Collectors

@Service
class TimetableService {

    fun getListOfNextTwoWeeks(): List<LocalDate> {
        val now = LocalDate.now()
        val fourteenDaysLater = now.plusDays(14)

        return now
            .datesUntil(fourteenDaysLater)
            .collect(Collectors.toList())
    }
}