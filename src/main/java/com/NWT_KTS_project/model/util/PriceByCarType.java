package com.NWT_KTS_project.model.util;

import com.NWT_KTS_project.model.enums.CarType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "prices")
public class PriceByCarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name="car_type")
    private CarType carType;

    @Column(name="price")
    private double price;
}
