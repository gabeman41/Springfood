package com.example.food_app.Service.ServiceImpl;

import com.example.food_app.Request.CreateFoodRequest;
import com.example.food_app.Service.FoodService;
import com.example.food_app.model.Category;
import com.example.food_app.model.Dish;
import com.example.food_app.model.Food;
import com.example.food_app.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final  FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request, Category category, Dish dish) {
        // create food object
        Food food = new Food();
        food.setFoodcategory(category);
        food.setDish(dish);
        food.setDescription(request.getDescription());
        food.setImages(request.getImages());
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setIngredients(request.getIngredients());
        food.setSeasonal(request.isSeasonal());
        food.setVegetable(request.isVegetables());
        food.setCreatedDate(new Date());

        Food savedFood = foodRepository.save(food);

        // add food in dish
        dish.getFood().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setDish(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getDishFood(Long dishId, boolean isVegetables, boolean isNonVegetables, boolean isSeasonal, String foodCategory) {
        // get list of foods
        List<Food>foods = foodRepository.findByDishId(dishId);

        // filter through foods
        if (isVegetables){
            foods = filterByvegetable(foods,isVegetables);
        }
        if (isNonVegetables){
            foods = filterByNonvegetable(foods,isNonVegetables);
        }
        if (isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")){
            foods = filterByCategory(foods,foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodcategory()!= null){
                return food.getFoodcategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal)
                .collect(Collectors.toList());
    }

    private List<Food> filterByNonvegetable(List<Food> foods, boolean isNonVegetables) {
        return foods.stream().filter(food -> food.isVegetable()==false)
                .collect(Collectors.toList());
    }

    private List<Food> filterByvegetable(List<Food> foods, boolean isVegetables) {
        return foods.stream().filter(food -> food.isVegetable()==isVegetables)
                .collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.SearchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> opt = foodRepository.findById(foodId);
        if(opt.isEmpty()){
            throw new Exception("Food does not exist...");
        }
        return opt.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
