package com.example.food_app.Service;

import com.example.food_app.Request.AddCartItemRequest;
import com.example.food_app.model.Cart;
import com.example.food_app.model.CartItem;

public interface CartService {
    public CartItem addItemToCart (AddCartItemRequest request, String jwt) throws Exception;

    public CartItem updateCartItemQuantity (Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart (Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById (Long Id) throws Exception;

    public Cart findCartByUserId (Long userId) throws Exception;


    public Cart clearCart (Long userId) throws Exception;
}
