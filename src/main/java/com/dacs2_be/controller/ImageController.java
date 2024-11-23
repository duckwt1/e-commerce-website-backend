package com.dacs2_be.controller;

import com.dacs2_be.entity.Image;
import com.dacs2_be.service.ImageService;
import com.dacs2_be.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final UploadImageService uploadImageService;

    @Autowired
    public ImageController(ImageService imageService, UploadImageService uploadImageService) {
        this.imageService = imageService;
        this.uploadImageService = uploadImageService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Image>> getImagesOfProduct(@PathVariable long productId) {
        List<Image> images = imageService.getImagesByProductId(productId);
        return ResponseEntity.ok(images);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = uploadImageService.uploadProductImg(file, "product-name");
        return ResponseEntity.ok(imageUrl);
    }
}
