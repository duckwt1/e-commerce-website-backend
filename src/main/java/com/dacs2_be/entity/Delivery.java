package com.dacs2_be.entity;

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
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "delivery_name", length = 50)
    private String deliveryName;

    @Column(name = "delivery_fee", precision = 10, scale = 2)
    private BigDecimal deliveryFee;

    @Size(max = 255)
    @Nationalized
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "delivery")
    private Set<Order> orders = new LinkedHashSet<>();

}