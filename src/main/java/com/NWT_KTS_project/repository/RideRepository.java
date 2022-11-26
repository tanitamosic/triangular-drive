package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
}
