package at.bernhardfleck.TimeOverlappingTool.presentation.exception

import at.bernhardfleck.TimeOverlappingTool.exceptions.FileNotFoundException
import at.bernhardfleck.TimeOverlappingTool.exceptions.InternalServerErrorException
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher.ERROR_MESSAGE
import javax.servlet.RequestDispatcher.ERROR_STATUS_CODE
import javax.servlet.http.HttpServletRequest

@Controller
class ExceptionController : ErrorController {

    /**
     * This is the global error "handler". If none of the declared exceptions in the ExceptionHandler can be caught,
     * the exception will be indirectly caught here. This method leads to the ExceptionHandler again with a generic
     * InternalServerErrorException
     */
    @RequestMapping("/error")
    fun throwCustomException(request: HttpServletRequest) {
        val errorCode = request.getAttribute(ERROR_STATUS_CODE) as Int
        val errorMessage = request.getAttribute(ERROR_MESSAGE)

        if (errorCode == 404) throw FileNotFoundException()
        else throw InternalServerErrorException("$errorMessage")
    }
}