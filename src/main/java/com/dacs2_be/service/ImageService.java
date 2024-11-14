package com.dacs2_be.service;

import com.dacs2_be.entity.Image;

public interface ImageService {
    Image saveImage(Image image);
    Image getImage(Long id);
    void deleteImage(Long id);
}
