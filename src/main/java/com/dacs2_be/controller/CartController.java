package com.dacs2_be.controller;


import com.dacs2_be.dto.CartItemDTO;
import com.dacs2_be.entity.Cart;
import com.dacs2_be.entity.CartDetail;
import com.dacs2_be.repository.CartDetailRepository;
import com.dacs2_be.repository.CartRepository;
import com.dacs2_be.repository.UserRepository;
import com.dacs2_be.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<?> addProductToCart(@PathVariable Integer userId,@RequestParam int productId,@RequestParam  int quantity) {
        return cartService.addProductToCart(userId, productId, quantity);
    }

    @PutMapping("/update/{cartDetailId}")
    public ResponseEntity<?> updateProductInCart(@PathVariable Integer cartDetailId, @RequestParam Integer quantity) {
        return cartService.updateProductInCart(cartDetailId, quantity);
    }

    @DeleteMapping("/remove/{cartDetailId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Integer cartDetailId) {
        return cartService.removeProductFromCart(cartDetailId);
    }

    @GetMapping("findByUserId")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByUserId(@RequestParam Integer userId) {

        int cartId = cartService.getCartByUserId(userId).getId();

        return ResponseEntity.ok(cartService.getCartItemsByCartId(cartId));
    }


}
