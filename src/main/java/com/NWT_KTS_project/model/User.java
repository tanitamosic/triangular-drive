package com.NWT_KTS_project.model;

import lombok.Setter;
import lombok.Getter;
import com.NWT_KTS_project.model.enums.City;

@Getter
@Setter
public class User {

    private String name;
    private String lastName;
    private String email;
    private City city;
    private String phone;
    private String password;
    private String picture;
    private Boolean blocked;
    
}
