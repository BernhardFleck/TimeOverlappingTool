package at.bernhardfleck.TimeOverlappingTool.presentation.exception

import at.bernhardfleck.TimeOverlappingTool.exceptions.FileNotFoundException
import at.bernhardfleck.TimeOverlappingTool.exceptions.InternalServerErrorException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class ExceptionHandler {
    var errorCode = "500"
    var errorMessageShort = "Internal Server Error"
    val defaultErrorMessage = "No detailed message available"
    var errorMessageDetailed = defaultErrorMessage

    @ExceptionHandler(
        value = [
            IllegalArgumentException::class,
            MethodArgumentTypeMismatchException::class,
            FileNotFoundException::class,
            InternalServerErrorException::class
        ]
    )
    fun handle(exception: Exception): ModelAndView {
        val modelAndView = ModelAndView()

        setErrorAttributesAccordingTo(exception)
        addErrorAttributesTo(modelAndView)
        return modelAndView
    }

    private fun setErrorAttributesAccordingTo(exception: Exception) {
        when (exception) {
            is IllegalArgumentException -> {
                errorCode = "400"
                errorMessageShort = "You provided a wrong input"
                errorMessageDetailed = exception.message ?: defaultErrorMessage
            }
            is MethodArgumentTypeMismatchException -> {
                errorCode = "400"
                errorMessageShort = "The ID you provided wasn't in the correct format"
                errorMessageDetailed = exception.message ?: defaultErrorMessage
            }
            is FileNotFoundException -> {
                errorCode = "404"
                errorMessageShort = "The URL which was requested does not exist"
                errorMessageDetailed = exception.message ?: defaultErrorMessage
            }
            else -> errorMessageDetailed = exception.message ?: exception.javaClass.toString()
        }
    }

    private fun addErrorAttributesTo(modelAndView: ModelAndView) {
        modelAndView.viewName = "error"
        modelAndView.addObject("errorCode", errorCode)
        modelAndView.addObject("errorMessageShort", errorMessageShort)
        modelAndView.addObject("errorMessageDetailed", errorMessageDetailed)
    }

}