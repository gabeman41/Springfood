package com.example.food_app.Controller;

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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {
    private final  UserService userService;
    private final OrderService orderService;

    @GetMapping("/order/dish/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestParam (required = false) String order_status,
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        List<Order>orders = orderService.getDishOrder(id,order_status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}
