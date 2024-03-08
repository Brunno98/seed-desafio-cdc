package br.com.brunno.bookstore.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCategoryRequest {

    @NotBlank
    private String name;

    public Category toModel() {
        return new Category(name);
    }
}
