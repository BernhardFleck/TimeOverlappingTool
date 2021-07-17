package at.bernhardfleck.TimeOverlappingTool.presentation

import org.springframework.web.servlet.ModelAndView

abstract class BaseController {

    protected fun addErrorMessageToPage(exception: Exception, modelAndView: ModelAndView): ModelAndView {
        modelAndView.addObject("errorBarShallBeVisible", true)
        modelAndView.addObject("errorMessage", exception.message)
        return modelAndView
    }
}