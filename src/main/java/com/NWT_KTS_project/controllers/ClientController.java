package com.NWT_KTS_project.controllers;



import com.NWT_KTS_project.model.Address;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public void reviewRide(@PathVariable Integer rideId, @PathVariable Integer userId, @RequestBody String comment, @RequestBody Integer carRating, @RequestBody Integer driverRating){
        reviewService.sendRideReview(rideId, userId, carRating, driverRating, comment);
    }


    @GetMapping("requestRide/{userId}")
    public int requestRide(@PathVariable Integer userId,@RequestBody String stops,@RequestBody String passengers,@RequestBody boolean petFriendly, @RequestBody boolean babyFriendly){
        ArrayList<Address> addresses = addressService.getAddressesFromString(stops);
        ArrayList<Client> clients = userService.getClientsFromPassangersString(passengers);
        clients.add(0, (Client) userService.getUserById(userId));

        int numPassengers = passengers.split(";").length;
        Driver driver = driverService.getAvailableDriver(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), petFriendly, babyFriendly, numPassengers);
        if(driver == null) return -1;



        if (!paymentService.processPaymentForRide(clients,addresses,driver.getCar())) return -2;

        return rideService.createRide(driver, addresses, clients).getId();
    }


    @GetMapping("/get-total-expense/{clientId}/{date1}/{date2}")
    public ResponseEntity<Integer> getTotalClientExpense(@PathVariable Integer clientId, @PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-avg-expense/{clientId}/{date1}/{date2}")
    public ResponseEntity<Integer> getAvgClientExpense(@PathVariable Integer clientId, @PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-total-distance/{clientId}/{date1}/{date2}")
    public ResponseEntity<Integer> getTotalClientDistance(@PathVariable Integer clientId, @PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-avg-distance/{clientId}/{date1}/{date2}")
    public ResponseEntity<Integer> getAvgClientDistance(@PathVariable Integer clientId, @PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-total-ridecount/{clientId}/{date1}/{date2}")
    public ResponseEntity<Integer> getTotalDClientRidecount(@PathVariable Integer clientId, @PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-avg-ridecount/{clientId}/{date1}/{date2}")
    public ResponseEntity<Integer> getAvgClientRidecount(@PathVariable Integer clientId, @PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }


}
