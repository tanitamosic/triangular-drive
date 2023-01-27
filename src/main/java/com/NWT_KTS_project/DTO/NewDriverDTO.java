package com.NWT_KTS_project.DTO;

import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.enums.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewDriverDTO {

    private String name;
    private String lastName;
    private String email;
    private String password1;
    private String password2;
    private String phone;
    private City city;
    private Car car;
}
