package com.example.food_app.Request;

import com.example.food_app.model.Category;
import com.example.food_app.model.IngredientItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String>images;
    private Long dishId;
    private boolean vegetables;
    private boolean seasonal;
    private List<IngredientItem> ingredients;
}
