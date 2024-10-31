package com.example.food_app.Service.ServiceImpl;

import com.example.food_app.Request.OrderRequest;
import com.example.food_app.Service.CartService;
import com.example.food_app.Service.DishService;
import com.example.food_app.Service.OrderService;
import com.example.food_app.model.*;
import com.example.food_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    UserRepository userRepository;
    DishService dishService;
    AddressRepository addressRepository;
    CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User customer) throws Exception {
        // get delivery address
        Address shipAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shipAddress);

        // check and save customer address if its not in the database
        if(!customer.getAddresses().contains(savedAddress)){
            customer.getAddresses().add(savedAddress);
            userRepository.save(customer);
        }
        // find dish by id
        Dish dish = dishService.findDishById(order.getDishId());

        // create order object
        Order createdOrder = new Order();
        createdOrder.setCustomer(customer);
        createdOrder.setCreatedAt(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setDish(dish);

        // create orderItem using cartItem
        Cart cart = cartService.findCartByUserId(customer.getId());
        // find cartItem
        List<OrderItem> orderItems = new ArrayList<>();
        // loop through all the cartItems
        for(CartItem cartItem : cart.getItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            // save order item
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        // get totalPrice
        Long totalPrice = cartService.calculateCartTotals(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        //save created order
        Order saveOrder = orderRepository.save(createdOrder);

        // add order to dish
        dish.getOrders().add(saveOrder);
        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = foodOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
        || orderStatus.equals("COMPLETED")
        || orderStatus.equals("PENDING")) {

            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please Select a valid Order Status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = foodOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getCustomerOrder(Long customerId) throws Exception {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getDishOrder(Long dishId, String orderStatus) throws Exception {
        // get list of orders
        List<Order> orders = orderRepository.findByDishId(dishId);
        if (orderStatus != null){
            orders = orders.stream().filter(order -> order.getOrderStatus()
                    .equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order foodOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("Order not found");
        }
        return optionalOrder.get();
    }
}
