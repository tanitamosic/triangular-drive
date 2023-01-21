package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Address;
import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.Route;
import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.model.enums.RideStatus;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.repository.RideRepository;
import com.NWT_KTS_project.util.comparators.ride.RideComparator;
import com.NWT_KTS_project.util.comparators.ride.RideDateComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideService{

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverService driverService;

    public List<Ride> getRidesByUserId(int id, RideComparator comparator) {
        List<Ride> rides = rideRepository.findByPassengersId(id);
        rides.addAll(rideRepository.findByDriverId(id));
        if (comparator != null) {
            rides.sort(comparator);
        }
        return rides;
    }


    public void markRideAsFinished(int id){
        Ride ride = rideRepository.findById(id).get();
        ride.setStatus(RideStatus.FINISHED);
        rideRepository.save(ride);
    }


    public Ride getNextRideForDriver(int id){
        List<Ride> rides = rideRepository.findByDriverId(id);
        ArrayList<Ride> pendingRides = new ArrayList<Ride>();

        for (Ride ride : rides) {
            if (ride.getStatus() == RideStatus.PENDING) {
                pendingRides.add(ride);
            }
        }
        pendingRides.sort(new RideDateComparator());

        if(pendingRides.size()<=1){
            driverService.setDriverStatus(id, DriverStatus.AVAILABLE);
        }

        if (pendingRides.size() > 0) {
            return pendingRides.get(0);
        }
        return null;
    }


    public Ride createRide(Driver driver, List<Address> addresses, List<Client> clients){
        Ride ride = new Ride();
        ride.setDriver(driver);
        ride.setPassengers(clients);
        Route route = new Route();
        route.setStart(addresses.get(0));
        route.setDestination(addresses.get(addresses.size()-1));
        ArrayList<Address> stops = new ArrayList<Address>();
        for(int i = 1; i < addresses.size()-1; i++){
            stops.add(addresses.get(i));
        }
        route.setStops(stops);
        ride.setRoute(route);
        ride.setStatus(RideStatus.PENDING);
        rideRepository.save(ride);
        return ride;
    }
}
