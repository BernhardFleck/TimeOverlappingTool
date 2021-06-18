package at.bernhardfleck.TimeOverlappingTool.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDate.now

@SpringBootTest
class TimetableServiceTest(@Autowired val timetableService: TimetableService) {

    @Test
    fun `ensure list of the next two weeks starts with tomorrow`() {
        val now = now()
        val tomorrow = now.plusDays(1)
        val datesOfNextTwoWeeks: List<LocalDate>
        val firstDate: LocalDate

        datesOfNextTwoWeeks = timetableService.getListOfNextTwoWeeks()
        firstDate = datesOfNextTwoWeeks.get(0)

        assertThat(firstDate).isEqualTo(tomorrow)
    }

    @Test
    fun `ensure list of the next two weeks ends with today plus 14 days`() {
        val tomorrow = now().plusDays(1)
        val expectedEnd = tomorrow.plusDays(13) // because 14th day is exlusive
        val datesOfNextTwoWeeks: List<LocalDate>
        val lastDate: LocalDate
        val lastIndex: Int

        datesOfNextTwoWeeks = timetableService.getListOfNextTwoWeeks()
        lastIndex = datesOfNextTwoWeeks.size - 1
        lastDate = datesOfNextTwoWeeks.get(lastIndex)

        assertThat(lastDate).isEqualTo(expectedEnd)
    }


}