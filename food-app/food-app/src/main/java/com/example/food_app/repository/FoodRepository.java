package com.example.food_app.repository;

import com.example.food_app.model.Dish;
import com.example.food_app.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByDishId(Long dishId);

    @Query(value = "SELECT f FROM Food WHERE f.name LIKE %: keyword % " +
            "OR f.foodCategory.name LIKE %: keyword %", nativeQuery = true)
    List<Food>SearchFood(@Param("keyword") String keyword);
}
