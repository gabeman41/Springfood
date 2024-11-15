package com.example.food_app.Controller;

import com.example.food_app.Configuration.JwtProvider;
import com.example.food_app.Request.LoginRequest;
import com.example.food_app.Response.AuthResponse;
import com.example.food_app.Service.CustomerUserDetailsService;
import com.example.food_app.enums.USER_ROLE;
import com.example.food_app.model.Cart;
import com.example.food_app.model.User;
import com.example.food_app.repository.CartRepository;
import com.example.food_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
   private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomerUserDetailsService service;
    private final CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>CreateUserHandler(@RequestBody User user) throws Exception {
       User isEmailExist = userRepository.findByEmail(user.getEmail());
       if(isEmailExist != null ){
           throw new Exception("Email is already used with another account");
       }
       // if its null we create the user
        User createdUser = new User();
       createdUser.setEmail(user.getEmail());
       createdUser.setFullName(user.getFullName());
       createdUser.setRole(user.getRole());
       createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
       User savedUser = userRepository.save(createdUser);

       // cart for user
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        // authentication to generate token
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate the token
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register Successful");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> SignIn(@RequestBody LoginRequest request){
        // get email and password
        String username = request.getEmail();
        String password = request.getPassword();

        // create Authorities
        Authentication authentication = authenticate(username,password);

        //get Authorities
        Collection<? extends GrantedAuthority>authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successful");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }

    private Authentication authenticate(String username, String password) {

        // load user details
        UserDetails userDetails = service.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username..");
        }
        // if user is present we tru match the password in the database
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,
                userDetails.getAuthorities());
    }
}
