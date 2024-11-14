package com.dacs2_be.service;

import com.dacs2_be.dto.UserDTO;
import com.dacs2_be.entity.User;

public interface AuthenticationService {
    boolean sendResetMail(String email);

    User findByTActivationCode(String token);

    boolean changePassword(String email, String password);

    boolean activateAccount(String email,String code);

    boolean register(UserDTO userDTO);
}
