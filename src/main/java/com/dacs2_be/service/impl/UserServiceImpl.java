package com.dacs2_be.service.impl;

import com.dacs2_be.repository.UserRepository;
import com.dacs2_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dacs2_be.entity.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(int id) {
        return userRepository.findById(id).get();
    }
}
