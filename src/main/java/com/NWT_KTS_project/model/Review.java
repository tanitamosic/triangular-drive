package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "driver_rating")
    private Integer driverRating;
    @Column(name = "car_rating")
    private Integer carRating;
    @Column(name = "comment")
    private String comment;
    @Column(name = "passenger_id")
    private User passenger;

}
