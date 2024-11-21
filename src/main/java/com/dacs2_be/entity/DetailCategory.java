package com.dacs2_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Detail_Category")
public class DetailCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_category_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "detailCategory")
    @JsonIgnore
    private Set<ProductDetailCategory> productDetailCategories = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}