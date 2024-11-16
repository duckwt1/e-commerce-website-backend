package com.dacs2_be.service;

import com.dacs2_be.entity.Image;
import com.dacs2_be.entity.Product;

import java.util.List;


public interface ProductService {
    List<Product> findAll();

    Product findById(int id);

    List<Product> findByName(String name);

    Product create(Product product);

    Product update(Product product);

    void delete(int id);

    List<Image> findImagesByProductId(int id);
}
