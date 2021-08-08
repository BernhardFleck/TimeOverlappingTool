package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.SelectedDay
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.presentation.helper.DatesCreator.Companion.emptyParticipantsAndNotesMappedToDaysOf
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ResultService {

    private lateinit var map: Map<LocalDate, MutableMap<Participant, String>>

    fun getDaysMappedToParticipantsAndTheirNotesOf(survey: Survey): Map<LocalDate, MutableMap<Participant, String>> {
        map = emptyParticipantsAndNotesMappedToDaysOf(survey)
        fillMapWithParticipantsAndTheirNotesFrom(survey)
        return map
    }

    private fun fillMapWithParticipantsAndTheirNotesFrom(survey: Survey) {
        val submissions = survey.submissions
        submissions.forEach { fillMapWithParticipantAndTheirNoteFrom(it) }
    }

    private fun fillMapWithParticipantAndTheirNoteFrom(submission: Submission) {
        val selectedDays = submission.selectedDays
        val participant = submission.participant

        selectedDays.forEach {
            val selectedDay = getItWhenMapContains(it)
            val note = it.note

            selectedDay?.put(participant, note)
        }
    }

    private fun getItWhenMapContains(day: SelectedDay): MutableMap<Participant, String>? {
        return map.get(day.date)
    }
}