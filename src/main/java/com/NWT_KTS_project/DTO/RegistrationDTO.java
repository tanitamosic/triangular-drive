package com.NWT_KTS_project.DTO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class RegistrationDTO {

    public String name;
    public String lastName;
    public String email;
    public String password;
    public String phone;
    public String city;

}
