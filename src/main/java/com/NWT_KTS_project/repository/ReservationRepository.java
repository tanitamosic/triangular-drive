package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.Reservation;
import com.NWT_KTS_project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation, Integer> {

}
