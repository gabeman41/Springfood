package com.example.food_app.Service;

import com.example.food_app.Request.CreateFoodRequest;
import com.example.food_app.model.Category;
import com.example.food_app.model.Dish;
import com.example.food_app.model.Food;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest request, Category category, Dish dish);
    void deleteFood (Long foodId) throws Exception;

    public List<Food> getDishFood(Long dishId, boolean isVegetables,
                                  boolean isNonVegetables, boolean isSeasonal,
                                  String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById (Long foodId) throws  Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
