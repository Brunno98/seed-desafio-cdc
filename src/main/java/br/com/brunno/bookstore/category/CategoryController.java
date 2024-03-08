package br.com.brunno.bookstore.category;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final EntityManager entityManager;

    @Transactional
    @PostMapping
    public CreateCategoryResponse createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        Category category = createCategoryRequest.toModel();

        entityManager.persist(category);

        return CreateCategoryResponse.from(category);
    }
}
