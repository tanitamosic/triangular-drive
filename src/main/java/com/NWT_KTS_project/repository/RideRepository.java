package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

    public List<Ride> findByPassengersId(int id);//??????? WHAT THE FUCK?????????

    public List<Ride> findByDriverId(int id);

    @Query(nativeQuery = true, value="SELECT * FROM rides WHERE rides.driver_id=?1 and rides.status='PENDING'")
    public List<Ride> getPendingRide(Integer id);

    @Query(nativeQuery = true, value="SELECT * FROM rides WHERE rides.departure_time>=?1 AND rides.arrival_time<=?2 AND rides.status='FINISHED'")
    List<Ride> getRides(LocalDateTime dateTime1, LocalDateTime dateTime2);

    @Query(nativeQuery = true, value="SELECT * FROM rides WHERE rides.driver_id=?1 AND rides.departure_time>=?2 AND rides.arrival_time<=?3 AND rides.status='FINISHED'")
    List<Ride> getDriverRides(Integer driverId, LocalDateTime dateTime1, LocalDateTime dateTime2);

    @Query(nativeQuery = true, value="SELECT r.* " +
            "FROM rides r " +
            "JOIN ride_passengers rp ON r.id = rp.ride_id " +
            "WHERE rp.passenger_id = ?1 AND r.departure_time>=?2 AND r.departure_time<=?3;")
    List<Ride> getClientRides(Integer clientId, LocalDateTime dateTime1, LocalDateTime dateTime2);

}
