package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.persistence.SurveyRepository
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.stream.Collectors

@Service
class SurveyService(@Autowired val surveyRepository: SurveyRepository) {

    fun getNextTwoWeeks(): List<LocalDate> {
        val tomorrow = now().plusDays(1)
        val fourteenDaysLater = tomorrow.plusDays(14)

        return tomorrow
            .datesUntil(fourteenDaysLater)
            .collect(Collectors.toList())
    }

    fun getSubmissionFrom(surveyDTO: SurveyDTO): Submission {
        return Submission(
            participant = surveyDTO.participant,
            selectedDates = surveyDTO.selectedDates
        )
    }

    fun getParticipantFrom(surveyDTO: SurveyDTO): Participant {
        return surveyDTO.participant
    }

    fun add(submission: Submission, survey: Survey) {
        val submissions = survey.submissions
        submissions.add(submission)
    }

    fun add(participant: Participant, survey: Survey) {
        val participants = survey.participants
        participants.add(participant)
    }

    fun saveAfterValidationOf(survey: Survey): Survey {
        survey.validate()
        return surveyRepository.saveAndFlush(survey)
    }

    fun participation(survey: Survey, submission: Submission): Survey {
        val participant = submission.participant

        add(submission, survey)
        add(participant, survey)

        submission.validate()
        return surveyRepository.saveAndFlush(survey)
    }
}
