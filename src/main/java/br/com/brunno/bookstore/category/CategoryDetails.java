package br.com.brunno.bookstore.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryDetails {
    private Long id;
    private String name;

    public static CategoryDetails from (Category category) {
        return new CategoryDetails(category.getId(), category.getName());
    }
}
