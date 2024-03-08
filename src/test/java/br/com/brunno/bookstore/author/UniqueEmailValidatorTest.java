package br.com.brunno.bookstore.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@Import(UniqueEmailValidator.class)
@ExtendWith(SpringExtension.class)
class UniqueEmailValidatorTest {

    @MockBean
    AuthorRepository authorRepository;

    @Autowired
    UniqueEmailValidator uniqueEmailValidator;

    @Test
    @DisplayName("when find an author with same email then should invalidate")
    void test1() {
        CreateAuthorRequest request = new CreateAuthorRequest("name", "foo@email.com", "some description");
        doReturn(Optional.of(new Author())).when(authorRepository).findByEmailIgnoreCase(request.getEmail());
        Errors errors = Mockito.spy(Errors.class);

        uniqueEmailValidator.validate(request, errors);

        verify(errors).rejectValue(any(), any(), any());
    }

}