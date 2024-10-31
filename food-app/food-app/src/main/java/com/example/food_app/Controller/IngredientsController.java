package com.example.food_app.Controller;

import com.example.food_app.Request.IngredientCategoryRequest;
import com.example.food_app.Request.IngredientItemRequest;
import com.example.food_app.Service.IngredientsService;
import com.example.food_app.model.IngredientCategory;
import com.example.food_app.model.IngredientItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientsController {
    IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest request
            )throws Exception{
        IngredientCategory item = ingredientsService.createIngredientCategory(request.getName(),
                request.getDishId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientItem> createIngredientItem(
            @RequestBody IngredientItemRequest request
            )throws Exception{
        IngredientItem item = ingredientsService.createIngredientItem(request.getDishId(),
                request.getName(), request.getCategoryId());
        return new ResponseEntity<>(item,HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientItem> updateIngredientStoke(
            @PathVariable Long id
    )throws Exception{
        IngredientItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    @GetMapping("/dish/{id}")
    public ResponseEntity<List<IngredientItem>> getDishIngredients(
            @PathVariable Long id
    )throws Exception{
        List<IngredientItem> item = ingredientsService.findDishIngredients(id);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    @GetMapping("/dish/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getDishIngredientCategory(
            @PathVariable Long id
    )throws Exception{
        List<IngredientCategory> items = ingredientsService.findIngredientCategoryDishId(id);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }


}
