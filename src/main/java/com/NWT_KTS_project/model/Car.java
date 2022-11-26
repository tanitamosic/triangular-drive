package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.enums.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "model")
    private String model;
    @Enumerated(EnumType.STRING)
    @Column(name = "make")
    private CarMake make;
    @Column(name = "seats")
    private Integer seats;
    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;
    @Column(name = "pet_friendly")
    private Boolean petFriendly;
    @Column(name = "baby_friendly")
    private Boolean babyFriendly;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CarType type;
    @Column(name = "score")
    private Float score;
    @JsonIgnore
    private String description;

    public Car(int id, String model, CarMake make, Integer seats, Color color,
               Boolean petFriendly, Boolean babyFriendly, CarType type, Float score) {
        this.id = id;
        this.model = model;
        this.make = make;
        this.seats = seats;
        this.color = color;
        this.petFriendly = petFriendly;
        this.babyFriendly = babyFriendly;
        this.type = type;
        this.score = score;
        this.description = this.type.desctiption;
    }
}



