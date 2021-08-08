package at.bernhardfleck.TimeOverlappingTool.presentation.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

class SelectedDayDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var date: LocalDate? = null

    lateinit var note: String

    constructor()

    constructor(
        date: LocalDate,
        note: String,
    ) {
        this.date = date
        this.note = note
    }
}