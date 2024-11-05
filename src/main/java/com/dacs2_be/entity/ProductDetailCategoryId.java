package com.dacs2_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProductDetailCategoryId implements java.io.Serializable {
    private static final long serialVersionUID = 6683946488757430080L;
    @NotNull
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @NotNull
    @Column(name = "detail_category_id", nullable = false)
    private Integer detailCategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductDetailCategoryId entity = (ProductDetailCategoryId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.detailCategoryId, entity.detailCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, detailCategoryId);
    }

}