package at.bernhardfleck.TimeOverlappingTool.domain

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDate
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class Submission(
    @OneToOne
    var participant: Participant,

    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection
    var selectedDates: List<LocalDate>

) : AbstractPersistable<UUID>()
