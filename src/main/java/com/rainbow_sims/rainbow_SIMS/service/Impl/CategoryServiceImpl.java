package com.rainbow_sims.rainbow_SIMS.service.Impl;

import com.rainbow_sims.rainbow_SIMS.model.Category;
import com.rainbow_sims.rainbow_SIMS.repository.CategoryRepository;
import com.rainbow_sims.rainbow_SIMS.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        // Check if a category with the same name already exists
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category with name '" + category.getCategoryName() + "' already exists");
        }

        // Generate categoryId and save the category
        category.setCategoryId(generateCategoryId());
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category categoryDetails) {
        // Retrieve the existing category
        Category category = getCategoryById(id);

        // Check if the new category name is already used by another category
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryDetails.getCategoryName());
        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(id)) {
            throw new RuntimeException("Category with name '" + categoryDetails.getCategoryName() + "' already exists");
        }

        // Update fields and save
        category.setCategoryName(categoryDetails.getCategoryName());
        category.setIsActive(categoryDetails.getIsActive());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private String generateCategoryId() {
        long count = categoryRepository.count() + 1;
        return String.format("ct%05d", count);
    }
}
