package br.com.brunno.bookstore.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestControllerErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorsResponse handle(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ValidationErrorsResponse validationErrorsResponse = new ValidationErrorsResponse();
        validationErrorsResponse.addGlobalErros(globalErrors);
        validationErrorsResponse.addFieldErrors(fieldErrors);

        return validationErrorsResponse;
    }
}
