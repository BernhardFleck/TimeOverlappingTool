package at.bernhardfleck.TimeOverlappingTool.presentation

import at.bernhardfleck.TimeOverlappingTool.presentation.dto.DtoToEntityConverter.Companion.convert
import at.bernhardfleck.TimeOverlappingTool.presentation.dto.SurveyDTO
import at.bernhardfleck.TimeOverlappingTool.presentation.helper.DatesCreator.Companion.getNextTwoWeeks
import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("/survey")
class SurveyController(@Autowired val service: SurveyService) : BaseController() {

    private val surveyViewName = "survey"
    private val shareSurveyViewName = "shareSurvey"

    @GetMapping("/create")
    fun showSurveyCreationPage(): ModelAndView {
        val modelAndView = ModelAndView()
        val surveyDTO = SurveyDTO()
        val datesOfNextTwoWeeks = getNextTwoWeeks()

        surveyDTO.selectedDays = datesOfNextTwoWeeks
        modelAndView.viewName = surveyViewName
        modelAndView.addObject("dto", surveyDTO)
        modelAndView.addObject("intention", "createSurvey")
        return modelAndView
    }

    @PostMapping("/create")
    fun createSurvey(@ModelAttribute surveyDTO: SurveyDTO): ModelAndView {
        return try {
            createSurveyOf(surveyDTO)
        } catch (exception: IllegalArgumentException) {
            addErrorMessageToPage(exception, showSurveyCreationPage())
        }
    }

    private fun createSurveyOf(surveyDTO: SurveyDTO): ModelAndView {
        val modelAndView = ModelAndView()
        val submission = service.getSubmissionFrom(surveyDTO)
        var survey = convert(surveyDTO)

        service.participation(survey, submission)
        survey.validate()
        survey = service.save(survey)

        modelAndView.viewName = shareSurveyViewName
        modelAndView.addObject("surveyId", survey.id)
        return modelAndView
    }

    @GetMapping("/participate/{surveyId}")
    fun showParticipationPage(@PathVariable(required = true) surveyId: UUID): ModelAndView {
        return try {
            showParticipationPageOf(surveyId)
        } catch (exception: MethodArgumentTypeMismatchException) {
            addErrorMessageToPage(exception, showSurveyCreationPage())
        }
    }

    @GetMapping("/participate")
    fun showParticipationPageOf(@RequestParam(required = true) surveyId: UUID): ModelAndView {
        return try {
            loadParticipationPageOf(surveyId)
        } catch (exception: IllegalArgumentException) {
            addErrorMessageToPage(exception, showSurveyCreationPage())
        } catch (exception: MethodArgumentTypeMismatchException) {
            addErrorMessageToPage(exception, showSurveyCreationPage())
        }
    }

    private fun loadParticipationPageOf(surveyId: UUID): ModelAndView {
        val modelAndView = ModelAndView()
        val survey = service.getSurveyBy(surveyId)
        val surveyDTO = convert(survey)

        modelAndView.viewName = surveyViewName
        modelAndView.addObject("dto", surveyDTO)
        modelAndView.addObject("surveyId", surveyId)
        modelAndView.addObject("intention", "participateSurvey")
        return modelAndView
    }

    @PostMapping("/participate/{surveyId}")
    fun participateInSurvey(
        @ModelAttribute surveyDTO: SurveyDTO,
        @PathVariable(required = true) surveyId: String
    ): ModelAndView {
        return try {
            participation(surveyId, surveyDTO)
            redirectToSharingPage(surveyId)
        } catch (exception: IllegalArgumentException) {
            addErrorMessageToPage(exception, showSurveyCreationPage())
        }
    }

    private fun participation(surveyId: String, surveyDTO: SurveyDTO) {
        val surveyId = UUID.fromString(surveyId)
        val submission = service.getSubmissionFrom(surveyDTO)
        val survey = service.getSurveyBy(surveyId)

        submission.validate()
        service.participation(survey, submission)
        service.save(survey)
    }

    private fun redirectToSharingPage(surveyId: String): ModelAndView {
        val resultPageUrl = "redirect:/survey/result/$surveyId"
        return ModelAndView(resultPageUrl)
    }

}