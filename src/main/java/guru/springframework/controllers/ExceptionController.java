package guru.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleConvertException(Exception exception) {

        return genericHandler4xxException(exception, "400 Bad Request");
    }

    public ModelAndView genericHandler4xxException(Exception exception, String httpStatus) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("4xxError");
        mav.addObject("exception", exception);
        mav.addObject("httpStatus", httpStatus);

        return mav;
    }
}
