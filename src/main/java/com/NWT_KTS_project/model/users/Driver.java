package com.NWT_KTS_project.model.users;

import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.enums.DriverStatus;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("DR")
public class Driver extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;
    @Column(name = "score")
    private Float score;
    // TODO: da li cuvati ovde?
    // private List<Ride> rideHistory;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DriverStatus status;

}
