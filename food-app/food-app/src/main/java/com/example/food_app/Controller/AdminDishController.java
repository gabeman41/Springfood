package com.example.food_app.Controller;

import com.example.food_app.Request.CreateDishRequest;
import com.example.food_app.Response.MessageResponse;
import com.example.food_app.Service.DishService;
import com.example.food_app.Service.UserService;
import com.example.food_app.model.Dish;
import com.example.food_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dishes")
@RequiredArgsConstructor
public class AdminDishController {
    DishService dishService;
    UserService userService;

    @PostMapping()
    public ResponseEntity<Dish> createRequest(@RequestBody CreateDishRequest request,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        // get user from jwt token
        User user = userService.findUserByJwtToken(jwt);

        // create Dish
        Dish dish = dishService.createDish(request,user);
        return new ResponseEntity<>(dish, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@RequestBody CreateDishRequest request,
                                           @RequestHeader("Authorization") String jwt,
                                           @PathVariable Long Id) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Dish dish = dishService.updateDish(Id,request);
        return new ResponseEntity<>(dish,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDish(@PathVariable Long Id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        dishService.deleteDish(Id);
        MessageResponse message = new MessageResponse();
        message.setMessage("Dish Deleted Successfully");
        return  new ResponseEntity<>(message,HttpStatus.OK);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Dish> updateDishStatus(@PathVariable Long Id,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Dish dish = dishService.updateDishStatues(Id);
        return new ResponseEntity<>(dish,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Dish> findDishByUserId(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Dish dish = dishService.getDishByUserId(user.getId());
        return new ResponseEntity<>(dish,HttpStatus.OK);
    }
}
