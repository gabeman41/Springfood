package com.example.food_app.Controller;

import com.example.food_app.Service.UserService;
import com.example.food_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User>findUserByJwtToken(@RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
