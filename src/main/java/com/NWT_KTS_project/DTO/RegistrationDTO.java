package com.NWT_KTS_project.DTO;

import com.NWT_KTS_project.model.enums.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    public String name;
    public String lastName;
    public String email;
    public String password1;
    public String password2;
    public String phone;
    public City city;

}
