package tstore.controllers.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tstore.exceptions.PageNotFoundException;

/**
 * Created by mipan on 08.11.2016.
 */
@ControllerAdvice
public class ExceptionController {
    final static Logger logger = Logger.getLogger(ExceptionController.class);
    @ExceptionHandler(PageNotFoundException.class)
    public ModelAndView handleCustomException(PageNotFoundException ex) {
        ModelAndView model = new ModelAndView("error/404");
        model.addObject("exception", ex);
        return model;

    }

//    todo add 403 error access

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {
        logger.error("Exeption controller: ", ex);
        ModelAndView model = new ModelAndView("error/Error");
        return model;
    }

}
