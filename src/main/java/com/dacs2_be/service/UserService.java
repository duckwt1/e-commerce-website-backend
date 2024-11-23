package com.dacs2_be.service;

import com.dacs2_be.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User create(User user);

    User update(User user);

    void delete(int id);

    User findByEmail(String email);

}
