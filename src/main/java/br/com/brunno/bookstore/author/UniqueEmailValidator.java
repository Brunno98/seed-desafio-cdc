package br.com.brunno.bookstore.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UniqueEmailValidator implements Validator {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateAuthorRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        CreateAuthorRequest request = (CreateAuthorRequest) target;
        String email = request.getEmail();

        Optional<Author> optionalAuthor = authorRepository.findByEmailIgnoreCase(email);

        if (optionalAuthor.isPresent()) {
            errors.rejectValue("email", null, "Email must be unique but already exits");
        }
    }
}
