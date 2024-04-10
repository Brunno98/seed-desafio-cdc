package br.com.brunno.bookstore.category;

import br.com.brunno.bookstore.shared.validator.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class CreateCategoryRequest implements NewCategoryDto {

    @NotBlank
    @UniqueValue(fieldName = "name", domainClass = Category.class)
    private String name;

    @Override
    public String getName() {
        return name;
    }
}
