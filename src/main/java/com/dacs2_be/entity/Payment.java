package com.dacs2_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @Column(name = "payment_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "payment_name", length = 50)
    private String paymentName;

    @Column(name = "payment_fee", precision = 10, scale = 2)
    private BigDecimal paymentFee;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

}