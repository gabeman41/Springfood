package com.example.food_app.Service;

import com.example.food_app.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory (String name, Long userId) throws Exception;

    public List<Category> findCategoryByDishId(Long Id) throws Exception;

    public Category findCategoryById(Long Id) throws Exception;
}
