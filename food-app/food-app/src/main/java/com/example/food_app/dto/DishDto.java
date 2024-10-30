package com.example.food_app.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class DishDto {

    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;
    private Long id;
}
