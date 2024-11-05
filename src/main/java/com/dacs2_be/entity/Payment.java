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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "payment_name", length = 50)
    private String paymentName;

    @Column(name = "payment_fee", precision = 10, scale = 2)
    private BigDecimal paymentFee;

    @Size(max = 255)
    @Nationalized
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "payment")
    private Set<Order> orders = new LinkedHashSet<>();

}