package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.enums.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "driver_update_requests")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DriverUpdateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", unique = true, nullable = false)
    private int requestId;
    @Column(name = "driver_id", unique = false, nullable = false)
    private int driverId;
    @Column(name="pending", nullable = false)
    private boolean pending;
    @Column(name="approved", nullable = false)
    private boolean approved;
    @Column(name = "email", unique = false, nullable = false)
    private String email;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "city")
    private City city;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Override
    public String toString() {
        return this.getName() + " " + this.getLastName() + ", " + this.getEmail() + ", " + this.getPhone() + ", " + this.getCity();
    }
}
