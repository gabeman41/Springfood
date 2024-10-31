package com.example.food_app.Service;

import com.example.food_app.Request.CreateDishRequest;
import com.example.food_app.dto.DishDto;
import com.example.food_app.model.Dish;
import com.example.food_app.model.User;

import java.util.List;

public interface DishService {
    public Dish createDish(CreateDishRequest request, User user) throws Exception;

    public Dish updateDish(Long dishId, CreateDishRequest updateDish) throws Exception;

    public void deleteDish(Long dishId) throws Exception;

    //get all dish. it wil only be available to admin
    public List<Dish>getAllDish();


    public List<Dish> searchDish(String keyword);

    public Dish findDishById(Long Id)throws Exception;

    public DishDto addToFavorite(Long dishId, User user)throws Exception;

    public Dish updateDishStatues(Long Id)throws Exception;

    public Dish getDishByUserId(Long userId) throws Exception;



}
