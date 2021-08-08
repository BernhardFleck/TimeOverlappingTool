package at.bernhardfleck.TimeOverlappingTool.presentation.dto

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.presentation.helper.DatesCreator.Companion.getDaysInRangeOf

class DtoToEntityConverter {

    companion object {
        fun convert(dto: SurveyDTO): Survey {
            return Survey(
                dto.purpose,
                dto.startDate,
                dto.endDate,
                dto.minimumParticipants,
                participants = mutableListOf(),
                submissions = mutableListOf()
            )
        }

        fun convert(survey: Survey): SurveyDTO {
            return SurveyDTO(
                survey.purpose,
                survey.startDate,
                survey.endDate,
                survey.minimumParticipants,
                participant = Participant(),
                selectedDays = getDaysInRangeOf(survey)
            )
        }
    }
}