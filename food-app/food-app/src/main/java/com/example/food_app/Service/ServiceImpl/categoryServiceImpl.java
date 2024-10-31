package com.example.food_app.Service.ServiceImpl;

import com.example.food_app.Service.CategoryService;
import com.example.food_app.Service.DishService;
import com.example.food_app.model.Category;
import com.example.food_app.model.Dish;
import com.example.food_app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class categoryServiceImpl implements CategoryService {
    DishService dishService;
    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        // find dish
        Dish dish = dishService.getDishByUserId(userId);

        Category category = new Category();
        category.setName(name);
        category.setDish(dish);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByDishId(Long Id) throws Exception {
        Dish dish = dishService.getDishByUserId(Id);
        return categoryRepository.findByDishId(Id);
    }

    @Override
    public Category findCategoryById(Long Id) throws Exception {
        Optional<Category> opt = categoryRepository.findById(Id);
        if(opt.isEmpty()){
            throw new Exception("Category not found");
        }
        return opt.get();
    }
}
