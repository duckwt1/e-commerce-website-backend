package com.dacs2_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @Column(name = "role_id", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 50)
    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;
}