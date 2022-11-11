package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.Enumerations.CarMaker;
import com.NWT_KTS_project.model.Enumerations.CarType;
import com.NWT_KTS_project.model.Enumerations.Colors;

public class Car {

    private String model;
    private CarMaker maker;
    private Integer seats;
    private Colors color;
    private Boolean petFriendly;
    private Boolean babyFriendly;
    private CarType type;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarMaker getMaker() {
        return maker;
    }

    public void setMaker(CarMaker maker) {
        this.maker = maker;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public Boolean getPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(Boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public Boolean getBabyFriendly() {
        return babyFriendly;
    }

    public void setBabyFriendly(Boolean babyFriendly) {
        this.babyFriendly = babyFriendly;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }
}
