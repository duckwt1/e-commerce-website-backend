package com.dacs2_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dacs2_be.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductId(int productId);
}
