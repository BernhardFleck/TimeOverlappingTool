package at.bernhardfleck.TimeOverlappingTool.presentation.dto

import at.bernhardfleck.TimeOverlappingTool.domain.Participant
import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import java.time.LocalDate
import java.util.stream.Collectors.toList

class DtoToEntityConverter {

    companion object Converter {
        fun convert(dto: SurveyDTO): Survey {
            return Survey(
                dto.purpose,
                dto.startDate,
                dto.endDate,
                dto.minimumParticipantsForMatch,
                participants = mutableListOf(),
                submissions = mutableListOf()
            )
        }

        fun convert(survey: Survey): SurveyDTO {
            return SurveyDTO(
                survey.purpose,
                survey.startDate,
                survey.endDate,
                survey.minimumParticipantsForMatch,
                participant = Participant(),
                selectedDates = datesFromStartToEndOf(survey)
            )
        }

        private fun datesFromStartToEndOf(survey: Survey): List<LocalDate> {
            val start = survey.startDate
            val end = survey.endDate
            val endInclusive = end.plusDays(1)

            return start.datesUntil(endInclusive)
                .collect(toList())
        }
    }
}