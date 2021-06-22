package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.persistence.SurveyRepository
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SurveyService(@Autowired private val surveyRepository: SurveyRepository) {

    fun getSubmissionFrom(surveyDTO: SurveyDTO): Submission {
        return Submission(
            participant = surveyDTO.participant,
            selectedDates = surveyDTO.selectedDates
        )
    }

    fun add(submission: Submission, survey: Survey) {
        val submissions = survey.submissions
        submissions.add(submission)
    }

    fun add(participant: Participant, survey: Survey) {
        val participants = survey.participants
        participants.add(participant)
    }

    fun participation(survey: Survey, submission: Submission): Survey {
        val participant = submission.participant

        add(submission, survey)
        add(participant, survey)
        return save(survey)
    }

    fun save(survey: Survey): Survey {
        return surveyRepository.saveAndFlush(survey)
    }

    fun getSurveyBy(surveyId: UUID): Survey {
        return surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("Survey could not be loaded. It is either finished or did not exist") }
    }
}
