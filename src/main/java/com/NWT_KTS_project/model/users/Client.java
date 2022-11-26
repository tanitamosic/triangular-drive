package com.NWT_KTS_project.model.users;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.Route;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("CL")
public class Client extends User{

    @Column(name = "paypal")
    private String paypal;
    @Column(name = "wallet")
    private String wallet;
    @Column(name = "credit_available")
    private Float creditAvailable;
    @Column(name = "credit_reserved")
    private Float creditReserved;

    //TODO: da li cuvati ovde ili izvlaciti iz rides?
    //private List<Ride> rideHistory;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "saved_routes",
            joinColumns = {@JoinColumn(name = "passenger_id")},
            inverseJoinColumns = {@JoinColumn(name = "route_id")})
    private List<Route> savedRoutes;
}
