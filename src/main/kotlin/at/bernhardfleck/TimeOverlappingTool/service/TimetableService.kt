package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.persistence.SurveyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.stream.Collectors

@Service
class TimetableService(@Autowired val surveyRepository: SurveyRepository) {

    fun getListOfNextTwoWeeks(): List<LocalDate> {
        val tomorrow = now().plusDays(1)
        val fourteenDaysLater = tomorrow.plusDays(14)

        return tomorrow
            .datesUntil(fourteenDaysLater)
            .collect(Collectors.toList())
    }

    fun save(survey: Survey): Survey {
        return surveyRepository.saveAndFlush(survey)
    }
}