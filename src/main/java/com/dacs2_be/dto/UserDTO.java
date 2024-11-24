package com.dacs2_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private String email;

    private String password;

    private String name;

    private String firstname;

    private String lastname;

    private String gender;

    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

    private String activation_code;

    public boolean status;
}
