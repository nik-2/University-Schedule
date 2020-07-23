package by.puesosi.schedule.advice;

import by.puesosi.schedule.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * The type Exception interceptor.
 */
@ControllerAdvice
public class ExceptionInterceptor {

    /**
     * Handle error 404 model and view.
     *
     * @return the model and view
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleError404()   {
        return new ModelAndView("error/404");
    }
}
