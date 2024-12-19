package com.dacs2_be.controller;

import com.dacs2_be.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart-detail")
public class CartDetailController {
    @Autowired
    private CartDetailService cartDetailService;

    @RequestMapping("/{cartId}")
    public ResponseEntity<?> getUserCartDetail(@PathVariable int cartId) {
        return ResponseEntity.ok(cartDetailService.getCartItemsByCartId(cartId));
    }
}
