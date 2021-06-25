package at.bernhardfleck.TimeOverlappingTool.domain

import org.springframework.data.jpa.domain.AbstractPersistable
import java.util.*
import javax.persistence.Entity

@Entity
data class Participant(
    var firstName: String = "",
    var lastName: String = ""
) : AbstractPersistable<UUID>() {

    fun validate() {
        firstName.ifBlank { throw IllegalArgumentException("first name must not be blank") }
        lastName.ifBlank { throw IllegalArgumentException("last name must not be blank") }
    }
}
