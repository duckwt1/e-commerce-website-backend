package com.dacs2_be.dto;

import com.dacs2_be.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
        private int id;
        private int quantity;
        private double price;
        private Product product;
}
