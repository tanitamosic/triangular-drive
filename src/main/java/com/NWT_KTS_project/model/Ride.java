package com.NWT_KTS_project.model;
import com.NWT_KTS_project.model.enums.RideStatus;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ride_passengers",
                joinColumns = {@JoinColumn(name = "ride_id")},
                inverseJoinColumns = {@JoinColumn(name = "passenger_id")})
    private List<Client> passengers;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RideStatus status;
    @Column(name = "price")
    private Float price;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Review review;
}
