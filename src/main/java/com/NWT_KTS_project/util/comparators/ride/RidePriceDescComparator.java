package com.NWT_KTS_project.util.comparators.ride;

import com.NWT_KTS_project.model.Ride;

public class RidePriceDescComparator implements RideComparator {
    @Override
    public int compare(Ride o1, Ride o2) {
        if( o1.getPrice() > o2.getPrice() ) {
            return -1;
        } else if( o1.getPrice() < o2.getPrice() ) {
            return 1;
        } else {
            return 0;
        }
    }

}
