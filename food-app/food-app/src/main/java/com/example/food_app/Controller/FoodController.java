package com.example.food_app.Controller;

import com.example.food_app.Service.DishService;
import com.example.food_app.Service.FoodService;
import com.example.food_app.Service.UserService;
import com.example.food_app.model.Dish;
import com.example.food_app.model.Food;
import com.example.food_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final  UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String jwt,
                                                 @RequestParam String name) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Food> food = foodService.searchFood(name);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/dish/{dishId}")
    public ResponseEntity<List<Food>> getDish(@RequestParam boolean vegetable,
                                              @RequestParam boolean seasonal,
                                              @RequestParam boolean nonveg,
                                              @RequestParam (required = false) String category,
                                              @PathVariable Long dishId,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food>foods = foodService.getDishFood(dishId,vegetable,nonveg,seasonal,category);
        return new ResponseEntity<>(foods,HttpStatus.OK);
    }
}
