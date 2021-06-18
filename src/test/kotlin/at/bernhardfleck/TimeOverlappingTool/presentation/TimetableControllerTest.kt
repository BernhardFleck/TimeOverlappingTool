package at.bernhardfleck.TimeOverlappingTool.presentation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TimetableControllerTest(@Autowired val timetableController: TimetableController) {

    @Test
    fun `ensure that dependency injection works with timetableController`() {
        assertThat(timetableController).isNotNull
    }

}