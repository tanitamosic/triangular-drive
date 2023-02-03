package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Address;
import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.Route;
import com.NWT_KTS_project.model.enums.City;
import com.NWT_KTS_project.model.enums.RideStatus;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.repository.AddressRepository;
import com.NWT_KTS_project.repository.RideRepository;
import com.NWT_KTS_project.repository.RouteRepository;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TestService {


    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RouteRepository routeRepository;



    public Ride createRideForTest(){

        Driver d = (Driver) userRepository.findUserByEmail("milanakeljic@gmail.com");
        Client c = (Client) userRepository.findUserByEmail("markotrijanic@gmail.com");
        ArrayList<Client> clients = new ArrayList<>();
        clients.add(c);
        Address start = new Address();
        start.setCity(City.NS);
        start.setStreet("Kraljevica Marka");
        start.setNumber("12");
        start.setLatitude(45.26113606906363);
        start.setLongitude(19.8359706964161);
        addressRepository.save(start);
        Address end = new Address();
        end.setCity(City.NS);
        end.setStreet("Kraljevica Marka");
        end.setNumber("25");
        end.setLatitude(45.25992072843628);
        end.setLongitude(19.835795944327185);
        addressRepository.save(end);
        Route route = new Route();
        route.setStart(start);
        route.setDestination(end);
        route.setDistance(200f);
        route.setStops(new ArrayList<Address>());
        routeRepository.save(route);
        Ride r = new Ride();
        r.setDriver(d);
        r.setPassengers(clients);
        r.setRoute(route);
        r.setStatus(RideStatus.PENDING);
        rideRepository.save(r);

        return r;
    }
}
