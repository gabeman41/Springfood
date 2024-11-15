package com.example.food_app.Controller;

import com.example.food_app.Request.OrderRequest;
import com.example.food_app.Service.OrderService;
import com.example.food_app.Service.UserService;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User customer = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(request,customer);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt) throws Exception{
        User customer =userService.findUserByJwtToken(jwt);
        List<Order>orders = orderService.getCustomerOrder(customer.getId());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
