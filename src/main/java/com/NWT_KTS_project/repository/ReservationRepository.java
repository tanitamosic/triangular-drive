package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.Reservation;
import com.NWT_KTS_project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository  extends JpaRepository<Reservation, Integer> {

    @Query(nativeQuery = true, value="SELECT res.* FROM reservations res JOIN rides r ON r.id=res.ride_id " +
            "WHERE r.driver_id=?1 AND r.status='RESERVED'")
    public List<Reservation> findByDriver(Integer id);

    @Query(nativeQuery = true, value="SELECT res.* FROM reservations res JOIN rides r ON r.id=res.ride_id " +
            "JOIN ride_passengers rp ON r.id=rp.ride_id WHERE rp.passenger_id=?1 AND r.status='RESERVED'")
    public List<Reservation> findByPassenger(Integer id);

}
