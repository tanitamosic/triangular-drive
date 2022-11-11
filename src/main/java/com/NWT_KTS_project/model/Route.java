package com.NWT_KTS_project.model;

import java.util.List;

public class Route {
    private Address start;
    private List<Address> stops;
    private Address destination;
    private Float distance;

    public Address getStart() {
        return start;
    }

    public void setStart(Address start) {
        this.start = start;
    }

    public List<Address> getStops() {
        return stops;
    }

    public void setStops(List<Address> stops) {
        this.stops = stops;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}
