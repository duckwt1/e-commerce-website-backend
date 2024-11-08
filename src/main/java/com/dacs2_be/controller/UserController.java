package com.dacs2_be.controller;

import com.dacs2_be.entity.User;
import com.dacs2_be.service.impl.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.dacs2_be.util.Util.hashMethod;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserServices userServices, PasswordEncoder passwordEncoder) {
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestParam("email") String email, @RequestParam("password") String password) {
         userServices.signin(email, password);
        return ResponseEntity.ok("Sign in successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) throws Exception {
        if (userServices.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServices.registerUser(user, "ROLE_USER");
        return ResponseEntity.ok("Registration successful! Please check your email for the activation code.");
    }

    // Kích hoạt tài khoản
    @PostMapping("/activate")
    public ResponseEntity<String> activateUser(
            @RequestParam("email") String email,
            @RequestParam("code") String activationCode) {

        String result = userServices.activateUser(email, hashMethod(activationCode));

        if (result.equals("Activated successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) throws Exception {
        String result = userServices.forgotPassword(email);
        if (result.equals("Email sent successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestParam("newPassword") String newPassword) {

        String result = userServices.resetPassword(email, otp, newPassword);

        if (result.equals("Password reset successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}