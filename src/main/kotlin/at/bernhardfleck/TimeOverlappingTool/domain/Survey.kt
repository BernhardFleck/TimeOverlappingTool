package at.bernhardfleck.TimeOverlappingTool.domain

import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDate
import java.util.*
import javax.persistence.CascadeType.ALL
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class Survey(
    val purpose: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val minimumParticipantsForMatch: Int,

    @ManyToMany(cascade = [ALL])
    val participants: List<Participant>,

    @ElementCollection
    val listOfSelectedDates: List<LocalDate>
) : AbstractPersistable<UUID>() {

    fun validate() {
        if (minimumParticipantsForMatch < 2) throw IllegalArgumentException("it needs two people for a match at least")
        purpose.ifBlank { throw IllegalArgumentException("purpose must not be blank") }
        listOfSelectedDates.ifEmpty { throw IllegalArgumentException("creating a survey without selecting dates is pointless") }
        participants.ifEmpty { throw IllegalArgumentException("the creator has to be part of the participants at least") }
        participants.forEach { it.validate() }
    }
}
