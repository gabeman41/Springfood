package com.example.food_app.Service.ServiceImpl;

import com.example.food_app.Request.IngredientCategoryRequest;
import com.example.food_app.Service.DishService;
import com.example.food_app.Service.IngredientsService;
import com.example.food_app.model.Dish;
import com.example.food_app.model.IngredientCategory;
import com.example.food_app.model.IngredientItem;
import com.example.food_app.repository.IngredientCategoryRepository;
import com.example.food_app.repository.IngredientItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {
    private final IngredientItemRepository ingredientItemRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final DishService dishService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long dishId) throws Exception {
        // find dish
        Dish dish = dishService.findDishById(dishId);
        // create ingredientCategory
        IngredientCategory category = new IngredientCategory();
        category.setDish(dish);
        category.setName(name);
        return ingredientCategoryRepository.save(category);
    }


    @Override
    public IngredientCategory findIngredientCategoryById(Long Id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(Id);
        if (opt.isEmpty()){
            throw new Exception("Ingredient category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryDishId(Long Id) throws Exception {
        dishService.findDishById(Id);
        return ingredientCategoryRepository.findByDishId(Id);
    }

    @Override
    public List<IngredientItem> findDishIngredients(Long dishId) throws Exception {
        return ingredientItemRepository.findByDishId(dishId);
    }

    @Override
    public IngredientItem createIngredientItem(Long dishId, String ingredientName, Long categoryId) throws Exception {
        Dish dish = dishService.findDishById(dishId);
        IngredientCategory category = findIngredientCategoryById(categoryId);
        IngredientItem item = new IngredientItem();
        item.setName(ingredientName);
        item.setDish(dish);
        item.setCategory(category);

        // save ingredientItem
        IngredientItem ingredient = ingredientItemRepository.save(item);
        // add ingredient item
        category.getIngredients().add(ingredient);
        return ingredient;
    }

    @Override
    public IngredientItem updateStock(Long Id) throws Exception {
        // find ingredientItem
        Optional<IngredientItem> item = ingredientItemRepository.findById(Id);
        if(item.isEmpty()){
            throw new Exception("Ingredient not found");
        }
        IngredientItem ingredientItem = item.get();
        ingredientItem.setInStoke(!ingredientItem.isInStoke());
        return ingredientItemRepository.save(ingredientItem);
    }
}
