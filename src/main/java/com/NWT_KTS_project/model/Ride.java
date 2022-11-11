package com.NWT_KTS_project.model;
import com.NWT_KTS_project.model.enums.RideStatus;

import java.time.LocalDateTime;
import java.util.List;

import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ride {

    private Route route;
    private List<Client> passengers;
    private Driver driver;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private RideStatus status;
    private Float price;
}
