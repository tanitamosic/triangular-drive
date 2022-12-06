package com.NWT_KTS_project.util.comparators.ride;

import com.NWT_KTS_project.model.Ride;

public class RideDateDescComparator implements RideComparator {
    @Override
    public int compare(Ride o1, Ride o2) {
        int comp =  o1.getDepartureTime().compareTo(o2.getDepartureTime());
        if (comp == 0) {
            comp =  o1.getArrivalTime().compareTo(o2.getArrivalTime());
        }
        return -comp;
    }

}
