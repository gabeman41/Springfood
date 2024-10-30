package com.example.food_app.Service;

import com.example.food_app.enums.USER_ROLE;
import com.example.food_app.model.User;
import com.example.food_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
                if(user == null){
                    throw new UsernameNotFoundException("user not found with email"+username);
                }

                // we get user and its role
        USER_ROLE role = user.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();

                // add the roles
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),authorities);
    }
}
