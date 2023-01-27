package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query(nativeQuery = true, value="SELECT * FROM report WHERE report.solved=false")
    List<Report> getAllUnsolvedReports();
}
