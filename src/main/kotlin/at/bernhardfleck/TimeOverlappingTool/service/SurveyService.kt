package at.bernhardfleck.TimeOverlappingTool.service

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.SelectedDay
import at.bernhardfleck.TimeOverlappingTool.domain.Submission
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.persistence.SurveyRepository
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SelectedDayDTO
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors.toList

@Service
class SurveyService(@Autowired private val surveyRepository: SurveyRepository) {

    fun getSubmissionFrom(surveyDTO: SurveyDTO): Submission {
        val selectedDayDTOs = getOnlySelectedDaysFrom(surveyDTO)
        return Submission(
            participant = surveyDTO.participant,
            selectedDays = convertFrom(selectedDayDTOs)
        )
    }

    private fun getOnlySelectedDaysFrom(surveyDTO: SurveyDTO): MutableList<SelectedDayDTO> {
        return surveyDTO.selectedDays.stream()
            .filter { it.date != null }
            .collect(toList())
    }

    private fun convertFrom(dtoList: List<SelectedDayDTO>) =
        dtoList
            .map { SelectedDay(it.note, it.date!!) }
            .toList()

    fun participation(survey: Survey, submission: Submission): Survey {
        val participant = submission.participant

        add(submission, survey)
        add(participant, survey)
        return save(survey)
    }

    fun add(submission: Submission, survey: Survey) {
        val submissions = survey.submissions
        submissions.add(submission)
    }

    fun add(participant: Participant, survey: Survey) {
        val participants = survey.participants
        participants.add(participant)
    }

    fun save(survey: Survey): Survey {
        return surveyRepository.saveAndFlush(survey)
    }

    fun getSurveyBy(surveyId: UUID): Survey {
        return surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("Survey could not be loaded. It is either finished or did not exist") }
    }

    fun delete(survey: Survey) {
        surveyRepository.delete(survey)
    }
}
