package com.example.food_app.Request;

import lombok.Data;

@Data
public class IngredientCategoryRequest {
    private String name;
    private Long dishId;
}
