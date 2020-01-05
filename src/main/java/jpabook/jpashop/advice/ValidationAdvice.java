package jpabook.jpashop.advice;

import jpabook.jpashop.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ErrorMessage> handleValidationConflict(MethodArgumentNotValidException ex) {
        List<ErrorMessage> emList = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ErrorMessage em = new ErrorMessage();
            em.setStatus(HttpStatus.BAD_REQUEST.toString());
            em.setMessage("[" + fieldError.getField() + "] is " + fieldError.getDefaultMessage()
                    + ", rejected Value for [" + fieldError.getRejectedValue()+"]");
            emList.add(em);
        }

        return emList;
    }
}
