package br.com.brunno.bookstore.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategory {

    private final CategoryRepository categoryRepository;

    public Category create(NewCategoryDto categoryDto) {
        Category newCategory = new Category(categoryDto.getName());
        return categoryRepository.save(newCategory);
    }
}
