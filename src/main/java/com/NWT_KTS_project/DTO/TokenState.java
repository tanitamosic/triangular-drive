package com.NWT_KTS_project.DTO;

import com.NWT_KTS_project.model.users.Admin;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.model.users.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenState {

    private String accessToken;
    private Long expiresIn;
    private String role;
    private String lastPasswordResetDate;

    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String city;

    public TokenState(String jwt, long expiresIn, String role, User user) {
        this.accessToken = jwt;
        this.expiresIn = expiresIn;
        this.role = role;
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.city = user.getCity().name;
    }
}
