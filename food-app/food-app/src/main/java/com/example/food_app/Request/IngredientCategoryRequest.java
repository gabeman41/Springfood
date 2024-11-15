package com.example.food_app.Request;

import com.example.food_app.model.IngredientItem;
import lombok.Data;

import java.util.List;

@Data
public class IngredientCategoryRequest {
    private String name;
    private Long dishId;
}
