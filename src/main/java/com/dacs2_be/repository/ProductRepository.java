package com.dacs2_be.repository;

import com.dacs2_be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :name, '%')")
    List<Product> searchByNameLike(@Param("name") String name);
    //find by id
    Product findById(int id);
}
