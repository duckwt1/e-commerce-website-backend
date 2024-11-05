package com.dacs2_be.service.impl;

import com.dacs2_be.entity.Role;
import com.dacs2_be.entity.User;
import com.dacs2_be.repository.RoleRepository;
import com.dacs2_be.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import static com.dacs2_be.util.Util.hashMethod;

@Service
public class UserServices implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServices(UserRepository userRepository, RoleRepository roleRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public void assignRole(User user, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        user.setRole(role);
        userRepository.save(user);
    }

    private String generateActivationCode() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(100000 + random.nextInt(900000));  // Mã 6 chữ số
    }


    // Gửi activation code cho người dùng mới đăng ký
    public void registerUser(User user, String roleName) throws MessagingException {

        String activationCode = generateActivationCode();
        user.setActivationCode(hashMethod(activationCode));
        user.setStatus(false);  // Tài khoản chưa kích hoạt

        Role role = roleRepository.findByRoleName(roleName);
        user.setRole(role);

        userRepository.save(user);
        emailService.sendActivationEmail(user.getEmail(), activationCode);
    }

    public String activateUser(String email, String activationCode) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "Email not found";
        }

        if (user.getActivationCode().equals(activationCode)) {
            user.setStatus(true);
            userRepository.save(user);
            return "Activated successfully";
        }
        return "Activation code is incorrect";
    }

    public String forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "Email not found";
        }

        String otp = generateActivationCode();
        emailService.sendForgotPasswordEmail(email, otp);

        return "Email sent successfully";
    }

    public String resetPassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }

        if (user.getActivationCode().equals(otp)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Password reset successfully";
        }
        return "OTP is incorrect";
    }

    public String signin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "Email not found";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Password is incorrect";
        }

        if (!user.getStatus()) {
            return "Account is not activated";
        }

        return "Sign in successfully";
    }

}
