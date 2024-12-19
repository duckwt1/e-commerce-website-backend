package com.dacs2_be.service;

import com.dacs2_be.dto.CartItemDTO;
import com.dacs2_be.entity.CartDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartDetailService {
    List<CartItemDTO> getCartItemsByCartId(Integer cartId);
    ResponseEntity<?> addProductToCart(Integer cartId, Integer productId, Integer quantity);
    ResponseEntity<?> updateProductInCart(Integer cartDetailId, Integer quantity);
    ResponseEntity<?> removeProductFromCart(Integer cartDetailId);
}
