package com.accounting.service;

import com.accounting.dto.CategoryResponse;
import com.accounting.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Long userId, Category category);

    Category updateCategory(Long userId, Long categoryId, Category category);

    boolean deleteCategory(Long userId, Long categoryId);

    List<CategoryResponse> listCategories(Long userId, Integer type);
}
