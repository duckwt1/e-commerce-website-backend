package com.dacs2_be.service.impl;


import com.dacs2_be.dto.CartItemDTO;
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
    public ResponseEntity<?> addProductToCart(Integer userId, int productId, int quantity) {
        try {
            // Find the product by its ID
            Product product = productRepository.findById(productId);

            if (product == null) {
                return ResponseEntity.badRequest().body("Product not found");
            }

            // Create or update the Cart
            Cart cart = cartRepository.findByUserId(userId);

            if (cart == null) {
                // Create a new cart if it doesn't exist
                cart = new Cart();
                cart.setUser(userRepository.findById(userId).orElse(null));
                cart.setTotalAmount(BigDecimal.ZERO); // Start with zero total amount
                cartRepository.save(cart);
            }

            // Create a CartDetail for the product
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(product);
            cartDetail.setQuantity(quantity);
            cartDetail.setCart(cart);

            // Calculate price for the CartDetail (assuming the Product has a price attribute)
            BigDecimal price = product.getSellPrice(); // Assuming the price field is 'sellPrice'
            cartDetail.setPrice(price);


            // Save the cart detail and cart
            cartDetailRepository.save(cartDetail);
            cartRepository.save(cart);

            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to add product to cart");
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
    public List<CartItemDTO> getCartItemsByCartId(Integer cartId) {
        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cartId);

        return cartDetails.stream().map(cartDetail -> {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartDetail.getId());
            cartItemDTO.setQuantity(cartDetail.getQuantity());
            cartItemDTO.setProduct(cartDetail.getProduct());
            return cartItemDTO;
        }).toList();
    }

    @Override
    public boolean createCart(String email) {
        Cart cart = new Cart();
        cart.setUser(userRepository.findByEmail(email));
        cartRepository.save(cart);
        return true;
    }
}
