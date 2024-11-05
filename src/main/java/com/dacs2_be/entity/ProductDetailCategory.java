package com.dacs2_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Product_Detail_Category")
public class ProductDetailCategory {
    @EmbeddedId
    private ProductDetailCategoryId id;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @MapsId("detailCategoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "detail_category_id", nullable = false)
    private DetailCategory detailCategory;

}