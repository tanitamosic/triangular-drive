package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.enums.*;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class Car {

    private String model;
    private CarMake maker;
    private Integer seats;
    private Color color;
    private Boolean petFriendly;
    private Boolean babyFriendly;
    private CarType type;
}



