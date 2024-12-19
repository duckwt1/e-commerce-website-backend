package com.dacs2_be.service.impl;


import com.dacs2_be.entity.Cart;
import com.dacs2_be.entity.CartDetail;
import com.dacs2_be.entity.Product;
import com.dacs2_be.repository.CartDetailRepository;
import com.dacs2_be.repository.CartRepository;
import com.dacs2_be.repository.ProductRepository;
import com.dacs2_be.repository.UserRepository;
import com.dacs2_be.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public ResponseEntity<?> addProductToCart(Integer userId, CartDetail cartDetail) {
        try {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElse(null));
            cart.setTotalAmount(cartDetail.getPrice().multiply(new BigDecimal(cartDetail.getQuantity())));
            cartRepository.save(cart);
        } else {
            cart.setTotalAmount(cart.getTotalAmount().add(cartDetail.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()))));
        }
        cartDetail.setCart(cart);
        List<Product> products = productRepository.searchByNameLike(cartDetail.getProduct().getName());
        if (!products.isEmpty()) {
            cartDetail.setProduct(products.get(0)); // Set the first matching product
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
        cartDetailRepository.save(cartDetail);
        return ResponseEntity.ok(cart);
     }catch (Exception e) {
        e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to add products");
        }
                }

    @Override
    public ResponseEntity<?> updateProductInCart(Integer cartDetailId, Integer quantity) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            cartDetail.setQuantity(quantity);
            cartDetailRepository.save(cartDetail);
            return ResponseEntity.ok(cartDetail);
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> removeProductFromCart(Integer cartDetailId) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            cartDetailRepository.delete(cartDetailOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public List<CartDetail> getCartItemsByCartId(Integer cartId) {
        System.out.println("cartId: " + cartId);
        return cartDetailRepository.findByCartId(cartId);
    }
}
