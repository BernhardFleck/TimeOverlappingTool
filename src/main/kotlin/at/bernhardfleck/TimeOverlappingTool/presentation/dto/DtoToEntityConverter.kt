package at.bernhardfleck.TimeOverlappingTool.presentation.dto

import at.bernhardfleck.TimeOverlappingTool.domain.Survey

class DtoToEntityConverter {

    companion object mapper {
        fun convert(dto: SurveyDTO): Survey {
            return Survey(
                dto.purpose,
                dto.startDate,
                dto.endDate,
                dto.minimumParticipantsForMatch,
                participants = listOf(dto.creator),
                dto.selectedDates
            )
        }
    }
}