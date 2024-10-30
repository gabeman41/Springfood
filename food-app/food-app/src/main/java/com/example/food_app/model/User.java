package com.example.food_app.model;

import com.example.food_app.dto.DishDto;
import com.example.food_app.enums.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<DishDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
}
