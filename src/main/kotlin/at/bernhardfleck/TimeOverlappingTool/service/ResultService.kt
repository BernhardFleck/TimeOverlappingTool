package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.presentation.helper.DatesCreator.Companion.emptyParticipantsMappedToDatesOf
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ResultService {

    private lateinit var map: Map<LocalDate, MutableList<Participant>>

    fun getDatesMappedToParticipantsOf(survey: Survey): Map<LocalDate, MutableList<Participant>> {
        map = emptyParticipantsMappedToDatesOf(survey)
        fillMapWithParticipantsFrom(survey)
        return map
    }

    private fun fillMapWithParticipantsFrom(survey: Survey) {
        val submissions = survey.submissions
        submissions.forEach { fillMapWithParticipantFrom(it) }
    }

    private fun fillMapWithParticipantFrom(submission: Submission) {
        val selectedDates = submission.selectedDates
        val participant = submission.participant

        selectedDates.forEach { getItWhenMapContains(it)?.add(participant) }
    }

    private fun getItWhenMapContains(date: LocalDate): MutableList<Participant>? {
        return map.get(date)
    }
}