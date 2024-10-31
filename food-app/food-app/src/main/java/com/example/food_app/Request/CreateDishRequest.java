package com.example.food_app.Request;

import com.example.food_app.model.Address;
import com.example.food_app.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateDishRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHour;
    private List<String>images;
}
