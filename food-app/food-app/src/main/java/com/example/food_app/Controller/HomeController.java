package com.example.food_app.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    public ResponseEntity<String> home (){
        return new ResponseEntity<>("welcome", HttpStatus.OK);
    }
}
