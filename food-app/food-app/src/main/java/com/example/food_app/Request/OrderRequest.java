package com.example.food_app.Request;

import com.example.food_app.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long dishId;
    private Address deliveryAddress;
}
