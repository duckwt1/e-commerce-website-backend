package com.dacs2_be.service.impl;


import com.cloudinary.Cloudinary;
import com.dacs2_be.entity.User;
import com.dacs2_be.repository.UserRepository;
import com.dacs2_be.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadImageServiceImpl implements UploadImageService {

    private final Cloudinary cloudinary;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String uploadImage(MultipartFile multipartFile, String name) {
        String url = "";
        String folderPath = "E-commerce img/user_avt/";
        try {
            url = cloudinary.uploader()
                    .upload(multipartFile.getBytes(), Map.of(
                                    "folder", folderPath,  // Đặt folder
                                    "public_id", name      // Tên file (public_id)
                            ))
                    .get("url")
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Lưu URL vào database
        User user = userRepository.findByName(name) ;
        user.setAvatar(url);
        userRepository.save(user);

        return url;
    }

    @Override
    public String uploadProductImg(MultipartFile multipartFile, String name) {
        String url = "";
        try {
            url = cloudinary.uploader()
                    .upload(multipartFile.getBytes(), Map.of("public_id", name))
                    .get("url")
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void deleteImage(String imgUrl) {
        try {
            String publicId = extractPublicIdFromUrl(imgUrl); // Bạn cần tách public_id từ URL
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractPublicIdFromUrl(String imgUrl) {
        String[] parts = imgUrl.split("/");
        String publicIdWithFormat = parts[parts.length - 1]; // Chỉ lấy phần cuối cùng của URL

        // Tách public_id và định dạng
        String[] publicIdAndFormat = publicIdWithFormat.split("\\.");
        return publicIdAndFormat[0]; // Lấy public_id
    }

}
