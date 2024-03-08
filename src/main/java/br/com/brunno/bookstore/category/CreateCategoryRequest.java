package br.com.brunno.bookstore.category;

import br.com.brunno.bookstore.shared.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCategoryRequest {

    @NotBlank
    @UniqueValue(fieldName = "name", domainClass = Category.class)
    private String name;

    public Category toModel() {
        return new Category(name);
    }
}
