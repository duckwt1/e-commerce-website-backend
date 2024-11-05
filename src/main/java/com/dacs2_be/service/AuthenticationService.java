package com.dacs2_be.service;

import com.dacs2_be.entity.User;

public interface AuthenticationService {
    boolean sendResetMail(String email);

    User findByToken(String token);

    boolean changePassword(User user);
}
