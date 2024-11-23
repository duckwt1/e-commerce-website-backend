package com.dacs2_be.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {
    String uploadImage(MultipartFile multipartFile, String name);
    String uploadProductImg(MultipartFile multipartFile, String name);
    void deleteImage(String imgUrl);
}
