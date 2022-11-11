package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.enums.City;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private City city;
    private String street;
    private String number;

    public String toString(){
        return street + " " + number + ", " + city.name;
    }
}
