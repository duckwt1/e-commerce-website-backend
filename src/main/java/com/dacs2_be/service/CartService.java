package com.dacs2_be.service;

import com.dacs2_be.entity.Cart;
import com.dacs2_be.entity.CartDetail;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

public interface CartService {
    Cart getCartByUserId(Integer userId);
    ResponseEntity<?> addProductToCart(Integer userId, CartDetail cartDetail);
    ResponseEntity<?> updateProductInCart(Integer cartDetailId, Integer quantity);
    ResponseEntity<?> removeProductFromCart(Integer cartDetailId);
}
