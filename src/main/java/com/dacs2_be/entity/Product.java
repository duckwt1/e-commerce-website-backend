package com.dacs2_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Size(max = 500)
    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "list_price", precision = 10, scale = 2)
    private BigDecimal listPrice;

    @Column(name = "sell_price", precision = 10, scale = 2)
    private BigDecimal sellPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Size(max = 255)
    @Nationalized
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "product")
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Feedback> feedbacks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Image> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<ProductDetailCategory> productDetailCategories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Review> reviews = new LinkedHashSet<>();

}