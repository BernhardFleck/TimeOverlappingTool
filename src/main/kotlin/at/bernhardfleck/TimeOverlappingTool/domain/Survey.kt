package at.bernhardfleck.TimeOverlappingTool.domain

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDate
import java.util.*
import javax.persistence.CascadeType.ALL
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table
data class Survey(
    val purpose: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val minimumParticipants: Int,

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = [ALL])
    val participants: MutableList<Participant>,

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = [ALL])
    val submissions: MutableList<Submission>

) : AbstractPersistable<UUID>() {

    fun validate() {
        if (minimumParticipants < 2) throw IllegalArgumentException("it needs two people for a match at least")
        purpose.ifBlank { throw IllegalArgumentException("purpose must not be blank") }
        participants.ifEmpty { throw IllegalArgumentException("the creator has to be part of the participants at least") }
        participants.forEach { it.validate() }
        submissions.ifEmpty { throw IllegalArgumentException("creating a survey where no submission was made should be impossible") }
        submissions.forEach {
            it.selectedDays.ifEmpty { throw IllegalArgumentException("creating a survey without selecting dates is pointless") }
        }
    }
}
