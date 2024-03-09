package br.com.brunno.bookstore.shared.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = IdExistsValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface IdExists {

    String message() default "does not exits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domain();

}
