package com.dacs2_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProductDetailCategoryId implements Serializable {
    private static final long serialVersionUID = -5924141729743870398L;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @NotNull
    @Column(name = "detail_category_id", nullable = false)
    private Integer detailCategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailCategoryId that = (ProductDetailCategoryId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(detailCategoryId, that.detailCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, detailCategoryId);
    }
}