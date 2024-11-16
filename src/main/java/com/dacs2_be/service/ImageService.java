package com.dacs2_be.service;

import com.dacs2_be.entity.Image;

import java.util.List;

public interface ImageService {
    Image saveImage(Image image);
    Image getImage(Long id);
    void deleteImage(Long id);
    List<Image> getImagesByProductId(Long productId);

}
