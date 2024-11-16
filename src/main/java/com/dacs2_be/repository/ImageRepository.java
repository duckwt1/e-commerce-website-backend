package com.dacs2_be.repository;

import com.dacs2_be.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long productId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.isThumbnail = false AND i.product.id = :productId")
    public void deleteImagesWithFalseThumbnailByBookId(@Param("productId") int productId);
}
