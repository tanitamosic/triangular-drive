package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.model.util.PriceByCarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceByCarTypeRepository extends JpaRepository<PriceByCarType, Integer> {
    @Query(nativeQuery = true, value="SELECT * FROM PRICES WHERE PRICES.car_type=?1")
    public double getPriceByType(CarType type);
}
