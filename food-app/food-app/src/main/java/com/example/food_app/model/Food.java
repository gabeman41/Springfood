package com.example.food_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Long price;

    @ManyToOne
    private Category category;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    private boolean available;

    @ManyToOne
    private Dish dish;

    private boolean isVegetable;
    private boolean isSeasonal;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<IngredientItem> ingredients = new ArrayList<>();

    private Date createdDate;

}
