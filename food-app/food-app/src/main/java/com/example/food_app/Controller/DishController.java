package com.example.food_app.Controller;

import com.example.food_app.Service.DishService;
import com.example.food_app.Service.UserService;
import com.example.food_app.dto.DishDto;
import com.example.food_app.model.Dish;
import com.example.food_app.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {
    private final  DishService dishService;
    private final  UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Dish>> searchDish(@RequestHeader("Authorization") String jwt,
                                                 @RequestParam String keyword) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Dish> dish = dishService.searchDish(keyword);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Dish>> getAllDish(@RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        List<Dish> dish = dishService.getAllDish();
        return new ResponseEntity<>(dish,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> findDishById(@RequestHeader("Authorization") String jwt,
                                                   @PathVariable Long id) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Dish dish = dishService.getDishByUserId(id);
        return new ResponseEntity<>(dish,HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorite")
    public ResponseEntity<DishDto> addFavorites(@PathVariable Long id,
            @RequestHeader("Authorization") String jwt
                                             ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        DishDto dish = dishService.addToFavorite(id,user);
        return  new ResponseEntity<>(dish,HttpStatus.OK);
    }
}
