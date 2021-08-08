package at.bernhardfleck.TimeOverlappingTool.domain

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import org.springframework.data.jpa.domain.AbstractPersistable
import java.util.*
import javax.persistence.CascadeType.ALL
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
data class Submission(
    @OneToOne
    var participant: Participant,

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = [ALL])
    var selectedDays: List<SelectedDay>

) : AbstractPersistable<UUID>() {

    fun validate() = participant.validate()

}
