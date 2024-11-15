package com.example.food_app.Service;

import com.example.food_app.Request.IngredientCategoryRequest;
import com.example.food_app.model.IngredientCategory;
import com.example.food_app.model.IngredientItem;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory createIngredientCategory(String name, Long dishId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long Id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryDishId(Long Id) throws Exception;

    public IngredientItem createIngredientItem(Long dishId, String ingredientName,
                                               Long categoryId) throws Exception;


    public List<IngredientItem> findDishIngredients(Long dishId) throws Exception;


    public IngredientItem updateStock (Long Id) throws Exception;

}
