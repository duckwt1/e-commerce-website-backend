package com.dacs2_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @Column(name = "image_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "url_image")
    private String urlImage;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "is_thumbnail")
    private Boolean isThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}