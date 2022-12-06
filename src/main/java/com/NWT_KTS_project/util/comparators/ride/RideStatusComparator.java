package com.NWT_KTS_project.util.comparators.ride;

import com.NWT_KTS_project.model.Ride;

public class RideStatusComparator implements RideComparator{

    @Override
    public int compare(Ride o1, Ride o2) {
        if( o1.getStatus().ordinal() > o2.getStatus().ordinal())
            return -1;
        else if( o1.getStatus().ordinal() < o2.getStatus().ordinal())
            return 1;
        else
            return 0;
    }
}
