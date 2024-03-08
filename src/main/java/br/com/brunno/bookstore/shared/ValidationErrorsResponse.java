package br.com.brunno.bookstore.shared;

import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationErrorsResponse {
    private final List<String> globalErrors = new ArrayList<>();
    private final List<Map<String, String>> fieldErrors = new ArrayList<>();

    public void addGlobalErros(List<ObjectError> errors) {
        errors.forEach(error -> this.globalErrors.add(error.getDefaultMessage()));
    }

    public void addFieldErrors(List<FieldError> errors) {
        errors.forEach(error -> {
            String field = error.getField();
            String defaultMessage = error.getDefaultMessage();
            Map<String, String> fieldError = Map.of("field", field, "message", defaultMessage);
            this.fieldErrors.add(fieldError);
        });
    }
}
