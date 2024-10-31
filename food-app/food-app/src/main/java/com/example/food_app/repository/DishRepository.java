package com.example.food_app.repository;

import com.example.food_app.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    // find by user
     Dish findByOwnerId(Long userId);

    // search for dish
    @Query(value = "SELECT r FROM Dish WHERE lower(r.name) LIKE lower" +
            "(concat('%':query, '%')) OR lower(r.cuisineType) LIKE lower" +
            "(concat('%':query,'%'))", nativeQuery = true)
    List<Dish>findBySearchQuery(String query);
}
