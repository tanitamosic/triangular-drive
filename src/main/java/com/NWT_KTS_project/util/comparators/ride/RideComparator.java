package com.NWT_KTS_project.util.comparators.ride;

import com.NWT_KTS_project.model.Ride;

import java.util.Comparator;

public interface RideComparator extends Comparator<Ride> {

    public int compare(Ride r1, Ride r2);

}

