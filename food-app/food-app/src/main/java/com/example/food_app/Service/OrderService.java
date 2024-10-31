package com.example.food_app.Service;

import com.example.food_app.Request.OrderRequest;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder (OrderRequest order, User customer) throws Exception;

    public Order updateOrder (Long orderId, String orderStatus) throws Exception;

    public void cancelOrder (Long orderId) throws Exception;

    public List<Order> getCustomerOrder(Long customerId) throws Exception;

    public List<Order> getDishOrder (Long dishId, String orderStatus) throws Exception;

    public Order foodOrderById (Long orderId) throws Exception;
}
