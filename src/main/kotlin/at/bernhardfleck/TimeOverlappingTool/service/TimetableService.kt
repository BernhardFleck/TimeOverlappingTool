package at.bernhardfleck.TimeOverlappingTool.service

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.stream.Collectors

@Service
class TimetableService {

    fun getListOfNextTwoWeeks(): List<LocalDate> {
        val tomorrow = now().plusDays(1)
        val fourteenDaysLater = tomorrow.plusDays(14)

        return tomorrow
            .datesUntil(fourteenDaysLater)
            .collect(Collectors.toList())
    }
}