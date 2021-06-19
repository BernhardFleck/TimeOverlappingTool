package at.bernhardfleck.TimeOverlappingTool.domain

import org.springframework.data.jpa.domain.AbstractPersistable
import java.util.*
import javax.persistence.Entity

@Entity
class Participant(firstName: String, lastName: String) : AbstractPersistable<UUID>()
