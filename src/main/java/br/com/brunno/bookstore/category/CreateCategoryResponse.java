package br.com.brunno.bookstore.category;

public record CreateCategoryResponse(
        Long id,
        String name
) {
    public static CreateCategoryResponse from(Category category) {
        return new CreateCategoryResponse(category.getId(), category.getName());
    }
}
