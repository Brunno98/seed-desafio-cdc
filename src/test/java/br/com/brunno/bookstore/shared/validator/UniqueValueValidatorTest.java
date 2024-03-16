package br.com.brunno.bookstore.shared.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.Payload;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.annotation.Annotation;
import java.util.List;

public class UniqueValueValidatorTest {

    EntityManager entityManager = Mockito.mock(EntityManager.class);

    @DisplayName("Deve lançar exceção quando a busco pelo valor retornar 2 ou mais resultados")
    @Test
    void assertTest() {
        Query query = Mockito.mock(Query.class);
        Mockito.doReturn(query).when(query).setParameter("value", 1);
        Mockito.doReturn(List.of(new Object(), new Object())).when(query).getResultList();
        Mockito.doReturn(query).when(entityManager).createQuery("SELECT 1 FROM java.lang.Object WHERE id = :value");

        UniqueValue uniqueValueConstraint = Mockito.mock(UniqueValue.class);
        Mockito.doReturn(Object.class).when(uniqueValueConstraint).domainClass();
        Mockito.doReturn("id").when(uniqueValueConstraint).fieldName();

        UniqueValueValidator uniqueValueValidator = new UniqueValueValidator(entityManager);
        uniqueValueValidator.initialize(uniqueValueConstraint);

        Assertions
                .assertThatIllegalStateException()
                .isThrownBy(() -> uniqueValueValidator.isValid(1, null));
    }
}
