package com.dacs2_be.controller;

import com.dacs2_be.dto.LoginRequest;
import com.dacs2_be.dto.UserDTO;
import com.dacs2_be.exception.ResourceNotFoundException;
import com.dacs2_be.security.JwtResponse;
import com.dacs2_be.service.AuthenticationService;
import com.dacs2_be.service.MailService;
import com.dacs2_be.service.UploadImageService;
import com.dacs2_be.service.UserService;
import com.dacs2_be.service.impl.UserServiceImpl;
import com.dacs2_be.service.jwt.JwtService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.dacs2_be.entity.User;

import java.util.Optional;

@RestController
public class HomeController {

    private final UserService userDetailsService;
    private final PasswordEncoder pe;
    private UserServiceImpl userServices;
    private final UploadImageService uploadImageService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    public HomeController(UserService userDetailsService, PasswordEncoder pe, UserServiceImpl userServices, UploadImageService uploadImageService) {
        this.userDetailsService = userDetailsService;
        this.pe = pe;
        this.userServices = userServices;
        this.uploadImageService = uploadImageService;
    }

    @RequestMapping({"/", "/index", "home"})
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }


//    @PostMapping("auth/login")
//    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws Exception {
//        User user = userServices.findByEmail(userDTO.getEmail());
//
//        if (user == null) {
//            throw new ResourceNotFoundException("User not found with email: " + userDTO.getEmail());
//        }
//
//        if (!pe.matches(userDTO.getPassword(), user.getPassword())) {
//            throw new ResourceNotFoundException("Password is incorrect");
//        }
//
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {

        if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        try{
            // authentication sẽ giúp ta lấy dữ liệu từ db để kiểm tra
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            // Nếu xác thực thành công
            if (authentication.isAuthenticated()) {
                // Tạo token cho người dùng
                final String jwtToken = jwtService.generateToken(loginRequest.getEmail());
                return ResponseEntity.ok(new JwtResponse(jwtToken));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Email or password is incorrect!");
        }
        return ResponseEntity.badRequest().body("Authentication failed");
    }

    @PostMapping("auth/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws Exception {
        if (userServices.findByEmail(userDTO.getEmail()) != null) {
            throw new ResourceNotFoundException("User already exists with email: " + userDTO.getEmail());
        }

        userDTO.setPassword(pe.encode(userDTO.getPassword()));

        if (authService.register(userDTO)) {

            return ResponseEntity.ok("User registered successfully.");
        }
        throw new ResourceNotFoundException("Cannot register user with email: " + userDTO.getEmail());
    }

    @GetMapping("auth/activate-account")
    public ResponseEntity<String> activateAccount(@RequestParam String email, @RequestParam String code) {
        boolean isActivated = authService.activateAccount(email, code);
        if (isActivated) {
            return ResponseEntity.ok("Account activated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Activation failed.");
        }
    }

    @PostMapping("auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) throws Exception {
        System.out.println(email);
        if (authService.sendResetMail(email)) {
            return ResponseEntity.ok("Reset password email sent successfully.");
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
            return ResponseEntity.ok(user);
        }
        throw exception;
    }

    @PostMapping("auth/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String code, @RequestBody String pass) throws Exception {
        User user = authService.findByTActivationCode(code);
        return ResponseEntity.ok(authService.changePassword(user.getEmail(), pe.encode(pass)));
    }

    @PostMapping("api/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        String imageUrl = uploadImageService.uploadImage(file, name);
        return ResponseEntity.ok(imageUrl);
    }

    @GetMapping("auth/get-user")
    public ResponseEntity<?> getUser(@RequestParam String email) {
        User user = userDetailsService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        throw new ResourceNotFoundException("User not found with email: " + email);
    }

    @PostMapping("auth/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        User user = userDetailsService.findByEmail(userDTO.getEmail());
        if (user != null) {
            System.out.println(userDTO.getPhoneNumber());
            user.setName(userDTO.getName());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setAddress(userDTO.getAddress());
            user.setGender(userDTO.getGender());
            user.setBirthDate(userDTO.getBirthDate());
            user.setFirstname(userDTO.getFirstname());
            user.setLastname(userDTO.getLastname());
            userDetailsService.patchUpdate(user);
            return ResponseEntity.ok(user);
        }
        throw new ResourceNotFoundException("User not found with email: " + userDTO.getEmail());
    }

    @PostMapping("auth/update-user-password")
    public ResponseEntity<?> updateUserPassword(@RequestParam String email, @RequestBody String newPassword) {
        User user = userDetailsService.findByEmail(email);
        if (user != null) {
           authService.changePassword(email, pe.encode(newPassword));
            return ResponseEntity.ok(user);
        }
        throw new ResourceNotFoundException("User not found with email: " + email);
    }

    @PostMapping("auth/register-shop")
    public ResponseEntity<?> registerShop(@RequestBody UserDTO userDTO) throws Exception {
        if (authService.registerShop(userDTO)) {

            return ResponseEntity.ok("Seller registered successfully.");
        }
        throw new ResourceNotFoundException("Cannot register user with email: " + userDTO.getEmail());
    }

}
