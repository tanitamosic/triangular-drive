package com.NWT_KTS_project.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "routes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"start_id", "destination_id"})})
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_id")
    private Address start;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "route_stops",
                joinColumns = {@JoinColumn(name = "route_id")},
                inverseJoinColumns = {@JoinColumn(name = "stop_id")})
    private List<Address> stops;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    private Address destination;

    @Column(name = "distance")
    private Float distance;

}