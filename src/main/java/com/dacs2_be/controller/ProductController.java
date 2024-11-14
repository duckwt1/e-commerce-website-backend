package com.dacs2_be.controller;

import com.dacs2_be.entity.Product;
import com.dacs2_be.exception.ResourceNotFoundException;
import com.dacs2_be.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> listProduct = productService.findAll();
        if (listProduct.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listProduct);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductsById(@PathVariable int id) {
        Product product = productService.findById(id);

        if (product == null) {
            throw new ResourceNotFoundException("Product not exist with id: " + id);
        }

        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        List<Product> product = productService.findByName(name);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not exist with name: " + name);
        }
        return ResponseEntity.ok(product);
    }
}
