package at.bernhardfleck.TimeOverlappingTool.domain

import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table
data class SelectedDay(
    var note: String = "",
    var date: LocalDate
) : AbstractPersistable<UUID>()