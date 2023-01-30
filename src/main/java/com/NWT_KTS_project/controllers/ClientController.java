package com.NWT_KTS_project.controllers;



import com.NWT_KTS_project.model.Address;
import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api/client")
public class ClientController {


    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RideService rideService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;


    @PostMapping("reviewRide/{rideId}/{userId}")
    public void reviewRide(@PathVariable Integer rideId, @PathVariable Integer userId, @RequestHeader String comment, @RequestHeader Integer carRating, @RequestHeader Integer driverRating){
        reviewService.sendRideReview(rideId, userId, carRating, driverRating, comment);
    }


    @GetMapping("requestRide/{userId}")
    public int requestRide(@PathVariable Integer userId,@RequestHeader String stops,@RequestHeader String passengers,@RequestHeader boolean petFriendly, @RequestHeader boolean babyFriendly,@RequestHeader CarType carType){
        ArrayList<Address> addresses = addressService.getAddressesFromString(stops);
        ArrayList<Client> clients = userService.getClientsFromPassangersString(passengers);
        clients.add(0, (Client) userService.getUserById(userId));
        //CarType type = CarType.valueOf(carType);

        int numPassengers = passengers.split(";").length;
        Driver driver = driverService.getAvailableDriver(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), petFriendly, babyFriendly, numPassengers, carType);
        if(driver == null) return -1;



        if (!paymentService.processPaymentForRide(clients,addresses,driver.getCar())) return -2;

        return rideService.createRide(driver, addresses, clients).getId();
    }



}
