package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.enums.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "city")
    private City city;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "number", nullable = false)
    private String number;

    public String toString(){
        return street + " " + number + ", " + city.name;
    }
}
