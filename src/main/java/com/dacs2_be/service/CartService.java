package com.dacs2_be.service;

import com.dacs2_be.dto.CartItemDTO;
import com.dacs2_be.entity.Cart;
import com.dacs2_be.entity.CartDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    Cart getCartByUserId(Integer userId);
    ResponseEntity<?> addProductToCart(Integer userId, int productId, int quantity);
    ResponseEntity<?> updateProductInCart(Integer cartDetailId, Integer quantity);
    ResponseEntity<?> removeProductFromCart(Integer cartDetailId);
    List<CartItemDTO> getCartItemsByCartId(Integer cartId);
    boolean createCart(String email);
}
