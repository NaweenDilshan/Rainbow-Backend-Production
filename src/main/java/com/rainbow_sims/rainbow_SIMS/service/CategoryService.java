package com.rainbow_sims.rainbow_SIMS.service;


import com.rainbow_sims.rainbow_SIMS.model.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}

