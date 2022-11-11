package com.NWT_KTS_project.model.users;

import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.enums.DriverStatus;

import java.util.List;

public class Driver extends User{

    private Car car;
    private Float score;
    private List<Ride> rideHistory;
    private DriverStatus status;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<Ride> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(List<Ride> rideHistory) {
        this.rideHistory = rideHistory;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }
}
