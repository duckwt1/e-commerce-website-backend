package com.dacs2_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @Column(name = "delivery_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "delivery_name", length = 50)
    private String deliveryName;

    @Column(name = "delivery_fee", precision = 10, scale = 2)
    private BigDecimal deliveryFee;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

}