package com.example.food_app.Controller;

import com.example.food_app.Request.CreateFoodRequest;
import com.example.food_app.Response.MessageResponse;
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

@RestController
@RequestMapping("/api/admin/food")
@RequiredArgsConstructor
public class AdminFoodController {
    FoodService foodService;
    UserService userService;
    DishService dishService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        // find Dish
        Dish dish =dishService.findDishById(request.getDishId());
        Food food = foodService.createFood(request,request.getCategory(),dish);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                                    @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        MessageResponse message = new MessageResponse();
        message.setMessage("Delete was Successful");
        return new ResponseEntity<>(message,HttpStatus.CREATED);

    }

    @PostMapping("/{id}")
    public ResponseEntity<Food> updateAvailabilityStatus(@PathVariable Long id,
                                                         @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food,HttpStatus.CREATED);

    }

}
