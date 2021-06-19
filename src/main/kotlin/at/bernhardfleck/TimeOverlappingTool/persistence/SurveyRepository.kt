package at.bernhardfleck.TimeOverlappingTool.persistence

import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SurveyRepository : JpaRepository<Survey, UUID>
