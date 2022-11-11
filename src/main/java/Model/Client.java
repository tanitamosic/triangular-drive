package Model;

import java.util.List;

public class Client extends User{

    private String paypal;
    private String wallet;
    private List<Ride> rideHistory;
    private List<Route> savedRoutes;

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public List<Ride> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(List<Ride> rideHistory) {
        this.rideHistory = rideHistory;
    }

    public List<Route> getSavedRoutes() {
        return savedRoutes;
    }

    public void setSavedRoutes(List<Route> savedRoutes) {
        this.savedRoutes = savedRoutes;
    }
}
