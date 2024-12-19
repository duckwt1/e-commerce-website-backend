package com.dacs2_be.service.impl;

import com.dacs2_be.dto.CartItemDTO;
import com.dacs2_be.entity.Cart;
import com.dacs2_be.entity.CartDetail;
import com.dacs2_be.entity.DetailCategory;
import com.dacs2_be.entity.Product;
import com.dacs2_be.repository.CartDetailRepository;
import com.dacs2_be.repository.CartRepository;
import com.dacs2_be.repository.ProductRepository;
import com.dacs2_be.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CartDetailServiceImpl  implements CartDetailService {

    @Autowired
    private  CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

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
    public ResponseEntity<?> addProductToCart(Integer userId, Integer productId, Integer quantity) {
        CartDetail cartDetail1 = new CartDetail();
        Product product = productRepository.findById(productId).orElse(null);
        Cart cart = cartRepository.findByUserId(userId);
        cartDetail1.setProduct(product);
        cartDetail1.setQuantity(quantity);
        cartDetail1.setCart(cart);

        return ResponseEntity.ok(cartDetailRepository.save(cartDetail1));
    }

    @Override
    public ResponseEntity<?> updateProductInCart(Integer cartDetailId, Integer quantity) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeProductFromCart(Integer cartDetailId) {
        return null;
    }
}
