package com.dacs2_be.service.impl;

import com.dacs2_be.dto.MailDTO;
import com.dacs2_be.dto.UserDTO;
import com.dacs2_be.repository.CartRepository;
import com.dacs2_be.repository.RoleRepository;
import com.dacs2_be.repository.UserRepository;
import com.dacs2_be.service.AuthenticationService;
import com.dacs2_be.service.MailService;
import com.dacs2_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dacs2_be.entity.Role;
import com.dacs2_be.entity.User;


import java.security.SecureRandom;

import static com.dacs2_be.util.Util.hashMethod;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailer;

    @Override
    public boolean changePassword(String email, String password) {
        User user = userService.findByEmail(email);
        if (user == null) {
            System.out.println("User not found");
            return false;
        }

        user.setPassword(password);
        System.out.println("Password changed + " + password);
        User updateUser = userService.update(user);
        return updateUser != null;
    }

    private String generateActivationCode() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(100000 + random.nextInt(900000));  // Mã 6 chữ số
    }

    public boolean register(UserDTO user) {

        if (userService.findByEmail(user.getEmail()) != null) {
            return false;
        }

        String activationCode = generateActivationCode();

        Role role = roleRepository.findByRoleName("ROLE_USER");

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setGender(user.getGender());
        newUser.setAddress(user.getAddress());
        newUser.setBirthDate(user.getBirthDate());
        newUser.setActivationCode(hashMethod(activationCode));
        newUser.setStatus(false); // Tài khoản chưa kích hoạt
        newUser.setRole(role);

        // Gửi email kích hoạt
        mailer.queue(getActivationMail(user.getEmail(), user.getName(), activationCode));

        // Create cart for user


        return userService.create(newUser) != null;
    }

    public boolean registerShop(UserDTO user) {

        User seller = userService.findByEmail(user.getEmail());

        if (seller == null) {
            return false;
        }

        Role role = roleRepository.findByRoleName("ROLE_SELLER");

        seller.setAddress(user.getAddress());
        seller.setPhoneNumber(user.getPhoneNumber());
        seller.setName(user.getName());
        seller.setRole(role);

        return userService.update(seller) != null;
    }

    @Override
    public boolean sendResetMail(String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            String activationCode = generateActivationCode();
            user.setActivationCode(hashMethod(activationCode));
            MailDTO mail = getResetMail(email, activationCode);
            mailer.queue(mail);
            userService.update(user);
            return true;
        }
        return false;
    }

    @Override
    public User findByTActivationCode(String code) {
        User user = userRepository.findByActivationCode(hashMethod(code));
        System.out.println(user.getEmail() + " find by code");
        return user;
    }

    private MailDTO getResetMail(String email, String code) {
        User user = userService.findByEmail(email);
        String name = user.getName();
        String link = "http://localhost:3000/reset-password";
        String url = String.format("%s/%s", link, code);
        String subject = "Password Reset Request";
        String button = "background-color:#783ecf;color:#fff;font-size:15px;padding:12px 10px;text-decoration:none;border-radius:3px;font-weight:bold";
        String body = "" +
                "<div style='width:50%;margin:0 auto'>" +
                "<div style='background-color:#f0f8ff;font-size:14px;padding:2em 5em'>" +
                "<img src='https://res.cloudinary.com/danm4jqyg/image/upload/v1730863628/E-commerce%20img/2620c875-6530-4629-ad18-578beca5d25a_n7l6en.jpg' referrerpolicy='no-referrer'>" +
                "<div style='display:flex;border:1px;height:1px;background:lightblue'></div>" +
                "<p>Hi <b>" + name + "</b>,</p>" +
                "<p>We have received a request to reset the password for the DealHub account associated with " + email + ". No changes have been made to your account.</p>" +
                "<p>You can reset your password by clicking the link below:</p>" +
                "<p style='display:grid;margin:0 auto;text-align:center'><a href=\"" + url + "\" style='" + button + "'>Reset Password</a></p>" +
                "<p>If you did not request a new password, please let us know immediately by replying to this email.</p>" +
                "<p>You can find answers to most questions and contact us at bavuongxathu@gmail.com. We are here to help you.</p>" +
                "<div style='display:flex;border:1px;height:1px;background:lightblue'></div>" +
                "<div style='margin-top:1em'>" +
                "<p>VKU, Hoa Quy, Ngu Hanh Son, Da Nang, Viet Nam</p>" +
                "Copyright © 2024 <b>DealHub</b>. All rights reserved." +
                "</div>" +
                "</div>" +
                "</div>";
        return new MailDTO(email, subject, body);
    }

    private MailDTO getActivationMail(String email, String name, String code) {
        User user = userRepository.findByEmail(email);
        String link = "http://localhost:3000/activate-account";
        String url = String.format("%s/%s/%s", link, email, code);
        String subject = "Account Activation Request";
        String button = "background-color:#783ecf;color:#fff;font-size:15px;padding:12px 10px;text-decoration:none;border-radius:3px;font-weight:bold";
        String body = "" +
                "<div style='width:50%;margin:0 auto'>" +
                "<div style='background-color:#f0f8ff;font-size:14px;padding:2em 5em'>" +
                "<img src='https://res.cloudinary.com/danm4jqyg/image/upload/v1730863628/E-commerce%20img/2620c875-6530-4629-ad18-578beca5d25a_n7l6en.jpg' referrerpolicy='no-referrer'>" +
                "<div style='display:flex;border:1px;height:1px;background:lightblue'></div>" +
                "<p>Hello <b>" + name + "</b>,</p>" +
                "<p>Thank you for signing up for a DealHub account associated with " + email + ".</p>" +
                "<p>Please activate your account by clicking the link below:</p>" +
                "<p style='display:grid;margin:0 auto;text-align:center'><a href=\"" + url + "\" style='" + button + "'>Activate Account</a></p>" +
                "<p>If you did not request this activation, please let us know immediately by replying to this email.</p>" +
                "<p>If you have any questions, please reach out to us at bavuongxathu@gmail.com. We are here to help.</p>" +
                "<div style='display:flex;border:1px;height:1px;background:lightblue'></div>" +
                "<div style='margin-top:1em'>" +
                "<p>VKU, Hoa Quy, Ngu Hanh Son, Da Nang, Viet Nam</p>" +
                "Copyright © 2024 <b>DealHub</b>. All rights reserved." +
                "</div>" +
                "</div>" +
                "</div>";
        return new MailDTO(email, subject, body);
    }

    @Override
    public boolean activateAccount(String email, String code) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        if (user.getActivationCode().equals(hashMethod(code))) {
            user.setStatus(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
