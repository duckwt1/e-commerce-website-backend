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

    public User patchUpdate(User user) {

        User userDB = userRepository.findByEmail(user.getEmail());

        if (user.getName() == null) {
            userDB.setName(userDB.getName());
        }

        if (user.getPhoneNumber() == null) {
            userDB.setPhoneNumber(userDB.getPhoneNumber());
        }

        if (user.getGender() == null) {
            userDB.setGender(userDB.getGender());
        }

        if (user.getBirthDate() == null) {
            userDB.setBirthDate(userDB.getBirthDate());
        }

        if (user.getAddress() == null) {
            userDB.setAddress(userDB.getAddress());
        }

        if (user.getFirstname() == null) {
            userDB.setFirstname(userDB.getFirstname());
        }

        if (user.getLastname() == null) {
            userDB.setLastname(userDB.getLastname());
        }

        return userRepository.save(user);

    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(int id) {
        return userRepository.findById(id).get();
    }

}
