package com.dacs2_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Voucher {
    @Id
    @Column(name = "voucher_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "voucher_name", length = 50)
    private String voucherName;

    @Column(name = "\"value\"", precision = 10, scale = 2)
    private BigDecimal value;

}