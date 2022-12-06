package com.NWT_KTS_project.service.implementations;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.repository.RideRepository;
import com.NWT_KTS_project.service.RideService;
import com.NWT_KTS_project.util.comparators.ride.RideComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceImplementation implements RideService {

    @Autowired
    private RideRepository rideRepository;


    @Override
    public List<Ride> getRidesByUserId(int id, RideComparator comparator) {
        List<Ride> rides = rideRepository.findByPassengersId(id);
        rides.addAll(rideRepository.findByDriverId(id));
        if (comparator != null) {
            rides.sort(comparator);
        }
        return rides;
    }
}
