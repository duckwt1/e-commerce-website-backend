package com.dacs2_be.controller;

import com.dacs2_be.entity.Image;
import com.dacs2_be.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Image>> getImagesOfProduct(@PathVariable long productId) {
        List<Image> images = imageService.getImagesByProductId(productId);
        return ResponseEntity.ok(images);
    }
}
