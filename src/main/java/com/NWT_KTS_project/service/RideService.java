package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.util.comparators.ride.RideComparator;

import java.util.List;

public interface RideService {

    public List<Ride> getRidesByUserId(int id, RideComparator comparator);
}
