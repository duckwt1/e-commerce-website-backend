package com.dacs2_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<DetailCategory> detailCategories = new LinkedHashSet<>();

}