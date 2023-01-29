package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

    public List<Ride> findByPassengersId(int id);//??????? WHAT THE FUCK?????????

    public List<Ride> findByDriverId(int id);

    @Query(nativeQuery = true, value="SELECT * FROM rides WHERE rides.driver_id=?1 and rides.status='PENDING'")
    public List<Ride> getPendingRide(Integer id);
}
