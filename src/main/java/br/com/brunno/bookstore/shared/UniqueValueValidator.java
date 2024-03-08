package br.com.brunno.bookstore.shared;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String domainAttribute;
    private Class<?> klass;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        List resultList = entityManager.createQuery("SELECT 1 FROM " + klass.getName() + " WHERE " + domainAttribute + " = :value")
                .setParameter("value", value)
                .getResultList();
        Assert.state(resultList.size() <= 1, "Finded more than 1 result with attribute value that should be unique");

        return resultList.isEmpty();
    }
}
