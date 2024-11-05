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
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "voucher_name", length = 50)
    private String voucherName;

    @Column(name = "\"value\"", precision = 10, scale = 2)
    private BigDecimal value;

    @OneToMany(mappedBy = "voucher")
    private Set<Order> orders = new LinkedHashSet<>();

}