package com.example.food_app.Service.ServiceImpl;

import com.example.food_app.Request.AddCartItemRequest;
import com.example.food_app.Service.CartService;
import com.example.food_app.Service.FoodService;
import com.example.food_app.Service.UserService;
import com.example.food_app.model.Cart;
import com.example.food_app.model.CartItem;
import com.example.food_app.model.Food;
import com.example.food_app.model.User;
import com.example.food_app.repository.CartItemRepository;
import com.example.food_app.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
   private final UserService userService;
    private final  CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final  FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        // find user, each user item must have cart
        User user = userService.findUserByJwtToken(jwt);
        // find Food
        Food food = foodService.findFoodById(request.getFoodId());
        // find cart
        Cart cart = cartRepository.findByCustomerId(user.getId());
        //update cartItem quantity
        for (CartItem cartItem : cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }
        // create new cartItem for user and add to cart if order is made
        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(request.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        //add cart item to cart
        cart.getItem().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if(optionalCartItem.isEmpty()){
            throw new Exception("cart item not found");
        }
         CartItem item = optionalCartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        // find user
        User user = userService.findUserByJwtToken(jwt);
        // find cart by user id
        Cart cart = cartRepository.findByCustomerId(user.getId());
        // get cartItem
        Optional<CartItem>optionalCartItem = cartItemRepository.findById(cartItemId);
        if(optionalCartItem.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item = optionalCartItem.get();
        //remove cartItem
        cart.getItem().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;
        // load through all cartItem present in the cart
        for(CartItem cartItem : cart.getItem()){
            total+= cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long Id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(Id);
        if(optionalCart.isEmpty()){
            throw new Exception("cart not found with id"+Id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
       // User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }


    @Override
    public Cart clearCart(Long userId) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
