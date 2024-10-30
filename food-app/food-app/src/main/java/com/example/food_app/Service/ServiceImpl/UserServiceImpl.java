package com.example.food_app.Service.ServiceImpl;

import com.example.food_app.Configuration.JwtProvider;
import com.example.food_app.Service.UserService;
import com.example.food_app.model.User;
import com.example.food_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        // extract email from jwt token
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found");
        }
        return user;
    }
}
