package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.users.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rideRequests")
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private int id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "rideRequest_passengers",
            joinColumns = {@JoinColumn(name = "rideRequest_id")},
            inverseJoinColumns = {@JoinColumn(name = "passenger_id")})
    private List<Client> passengers;

    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "accepted_user_id")
    //private List<Boolean> accepted;

}
