package com.dacs2_be.controller;

import com.dacs2_be.dto.UserDTO;
import com.dacs2_be.exception.ResourceNotFoundException;
import com.dacs2_be.service.AuthenticationService;
import com.dacs2_be.service.MailService;
import com.dacs2_be.service.UserService;
import com.dacs2_be.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.dacs2_be.entity.User;

import java.util.Optional;

@RestController
public class HomeController {

    private final UserService userDetailsService;
    private final PasswordEncoder pe;
    private UserServiceImpl userServices;

    @Autowired
    private MailService mailerService;

    @Autowired
    private AuthenticationService authService;


    @Autowired
    public HomeController(UserService userDetailsService, PasswordEncoder pe, UserServiceImpl userServices) {
        this.userDetailsService = userDetailsService;
        this.pe = pe;
        this.userServices = userServices;
    }

    @RequestMapping({"/", "/index", "home"})
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }


    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws Exception {
        User user = userServices.findByEmail(userDTO.getEmail());

        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userDTO.getEmail());
        }

        if (!pe.matches(userDTO.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Password is incorrect");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("auth/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws Exception {
        if (userServices.findByEmail(userDTO.getEmail()) != null) {
            throw new ResourceNotFoundException("User already exists with email: " + userDTO.getEmail());
        }

        userDTO.setPassword(pe.encode(userDTO.getPassword()));

        if (authService.register(userDTO)) {

            return ResponseEntity.ok().build();
        }
        throw new ResourceNotFoundException("Cannot register user with email: " + userDTO.getEmail());
    }

    @PostMapping("auth/activate")
    public ResponseEntity<?> activateUser(@RequestParam String code, @RequestParam String email) throws Exception {
        return ResponseEntity.ok(authService.activateAccount(email, code));
    }

    @PostMapping("auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) throws Exception {
        System.out.println(email);
        if (authService.sendResetMail(email)) {
            return ResponseEntity.ok().build();
        }
        throw new ResourceNotFoundException("User not exists with email: " + email);
    }

    @SneakyThrows
    @PostMapping("auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam Optional<String> code) throws Exception {
        ResourceNotFoundException exception = new ResourceNotFoundException("Cannot find token: " + code);
        String codeVal = code.orElseThrow(() -> exception);
        User user = authService.findByTActivationCode(codeVal);
        if (user != null) {
            // Go to change password page
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://localhost:8080/auth/change-password");
            return ResponseEntity.ok(user);
        }
        throw exception;
    }

    @PostMapping("auth/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(authService.changePassword(user.getEmail(), pe.encode(user.getPassword())));
    }

}
