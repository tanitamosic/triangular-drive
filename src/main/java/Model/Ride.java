package Model;

import Model.Enumerations.RideStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Ride {

    private Route route;
    private List<Client> passengers;
    private Driver driver;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private RideStatus status;
    private Float price;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Client> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Client> passengers) {
        this.passengers = passengers;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
