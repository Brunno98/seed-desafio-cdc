package br.com.brunno.bookstore.shared.validator;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class IdExistsValidator implements ConstraintValidator<IdExists, Object> {

    private final EntityManager entityManager;

    private Class<?> domainClass;

    @Override
    public void initialize(IdExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.domainClass = constraintAnnotation.domain();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        if (Objects.isNull(target)) return true;

        Object result = entityManager.find(domainClass, target);

        return Objects.nonNull(result);
    }
}
