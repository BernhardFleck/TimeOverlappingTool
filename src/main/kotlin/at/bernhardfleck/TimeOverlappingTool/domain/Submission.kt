package at.bernhardfleck.TimeOverlappingTool.domain

import org.springframework.data.jpa.domain.AbstractPersistable
import java.util.*
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
data class Submission(
    @OneToOne
    var participant: Participant,

    @OneToMany(cascade = [ALL])
    var selectedDays: List<SelectedDay>

) : AbstractPersistable<UUID>() {

    fun validate() = participant.validate()

}
