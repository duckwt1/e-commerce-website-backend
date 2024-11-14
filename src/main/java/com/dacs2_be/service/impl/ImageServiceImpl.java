package com.dacs2_be.service.impl;

import com.dacs2_be.repository.ImageRepository;
import com.dacs2_be.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dacs2_be.entity.Image;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image saveImage(Image image) {
        return null;
    }

    @Override
    public Image getImage(Long id) {
        return null;
    }

    @Override
    public void deleteImage(Long id) {

    }
}
