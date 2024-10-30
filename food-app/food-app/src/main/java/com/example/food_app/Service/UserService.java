package com.example.food_app.Service;

import com.example.food_app.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
