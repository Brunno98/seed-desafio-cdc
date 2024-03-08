package br.com.brunno.bookstore.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UniqueCategoryNameValidator implements Validator {

    public final CategoryRepository categoryRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateCategoryRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        CreateCategoryRequest request = (CreateCategoryRequest) target;

        Optional<Category> optionalCategory = categoryRepository.findByNameIgnoreCase(request.getName());

        if (optionalCategory.isPresent()) {
            errors.rejectValue("name", null, "Category with name " + request.getName() + " Already exists");
        }
    }
}
