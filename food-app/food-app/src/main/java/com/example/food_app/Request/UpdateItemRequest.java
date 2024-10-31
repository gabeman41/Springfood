package com.example.food_app.Request;

import lombok.Data;

@Data
public class UpdateItemRequest {
    private Long cartItemId;
    private int quantity;
}
