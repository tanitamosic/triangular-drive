package com.NWT_KTS_project.controllers;



import com.NWT_KTS_project.DTO.ReportDTO;
import com.NWT_KTS_project.model.*;
import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.model.enums.RideStatus;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    private ReportService reportService;


    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @PostMapping("reviewRide/{rideId}/{userId}")
    public void reviewRide(@PathVariable Integer rideId, @PathVariable Integer userId, @RequestHeader String comment, @RequestHeader Integer carRating, @RequestHeader Integer driverRating){
        reviewService.sendRideReview(rideId, userId, carRating, driverRating, comment);
    }


    @GetMapping("requestRide/{userId}")
    public int requestRide(@PathVariable Integer userId,@RequestHeader String stops,
                           @RequestHeader String passengers,@RequestHeader boolean petFriendly,
                           @RequestHeader boolean babyFriendly,@RequestHeader CarType carType,
                           @RequestHeader String price){
        ArrayList<Address> addresses = addressService.getAddressesFromString(stops);
        ArrayList<Client> clients;
        try {
             clients = userService.getClientsFromPassangersString(passengers);
        }catch (Exception e){
            return -3;
        }
        clients.add(0, (Client) userService.getUserById(userId));
        int numPassengers = passengers.split(";").length;
        if(passengers.equals("")){
            numPassengers = 0;
        }
        int numClients = clients.size();
        if(numPassengers+1 != numClients)
        {
            return -3;
        }


        Driver driver = driverService.getAvailableDriver(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), petFriendly, babyFriendly, numPassengers, carType);
        if(driver == null) return -1;

        if (!paymentService.processPaymentForRide(clients,addresses,driver.getCar())) return -2;

        Ride ride= rideService.createRide(driver, addresses, clients, RideStatus.PENDING,price);
        return ride.getId();
    }

    @GetMapping("make-reservation/{userId}")
    public int makeReservation(@PathVariable Integer userId,@RequestHeader String stops,
                               @RequestHeader String passengers,@RequestHeader boolean petFriendly,
                               @RequestHeader boolean babyFriendly,@RequestHeader CarType carType,
                               @RequestHeader String timeString, @RequestHeader String price){
        ArrayList<Address> addresses = addressService.getAddressesFromString(stops);
        ArrayList<Client> clients = userService.getClientsFromPassangersString(passengers);
        clients.add(0, (Client) userService.getUserById(userId));

        int numPassengers = passengers.split(";").length;
        Driver driver = driverService.reserveDriver(petFriendly, babyFriendly, numPassengers, carType);
        if(driver == null) return -1;

        if (!paymentService.processPaymentForRide(clients,addresses,driver.getCar())) return -2;

        LocalDateTime time = LocalDateTime.parse(timeString.substring(0,timeString.length()-5),TIME_FORMATTER);
        Reservation res = rideService.makeReservation(driver, addresses, clients, RideStatus.RESERVED,time,price);
        return res.getRide().getId();
    }


    @GetMapping("/get-client-rides/{clientId}/{date1}/{date2}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Ride>> getTotalClientExpense(@PathVariable Integer clientId, @PathVariable String date1, @PathVariable String date2) {
        LocalDate d1 = LocalDate.parse(date1, FORMATTER);
        LocalDateTime dateTime1 = d1.atStartOfDay();
        LocalDate d2 = LocalDate.parse(date2, FORMATTER);
        LocalDateTime dateTime2 = d2.atStartOfDay();
        List<Ride> retval = rideService.getClientRides(clientId, dateTime1, dateTime2);
        return new ResponseEntity<>(retval, HttpStatus.OK);
    }

    @PostMapping("add-funds/{clientId}/{amount}")
    @PreAuthorize("hasRole('CLIENT')")
    public float addFunds(@PathVariable Integer clientId,@PathVariable Double amount) {
        return paymentService.addFunds(clientId, Float.parseFloat(amount.toString()));
    }

    @GetMapping("get-funds/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public float getFunds(@PathVariable Integer clientId) {
        return paymentService.getFunds(clientId);
    }

    @PostMapping("/report")
    public void reportDriver(@RequestBody ReportDTO dto){
        Ride ride = rideService.getRideById(dto.rideId);
        reportService.makeReport(ride.getPassengers().get(0),ride.getDriver(),dto.reason);
    }

    @PostMapping("/favorite-route/{userId}/{routeId}")
    public void favoriteRoute(@PathVariable Integer userId, @PathVariable Integer routeId){
        userService.favoriteRoute(userId, routeId);
    }

    @GetMapping(value = "/get-favorites/{userId}")
    public ResponseEntity<List<Route>> getFavoriteRoutes(@PathVariable Integer userId){
        List<Route> routes = userService.getFavoriteRoutes(userId);
        return new ResponseEntity<List<Route>>(routes, HttpStatus.OK);
    }
}
