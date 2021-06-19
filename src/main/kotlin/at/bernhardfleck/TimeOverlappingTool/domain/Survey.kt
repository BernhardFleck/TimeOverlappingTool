package at.bernhardfleck.TimeOverlappingTool.domain

import lombok.Data
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDate
import java.util.*
import javax.persistence.CascadeType.ALL
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
@Data
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
        participants.forEach { it.validate() }
    }
}
