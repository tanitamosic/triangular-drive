package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Report;
import com.NWT_KTS_project.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    public List<Report> getAllUnsolvedReports() {
        return reportRepository.getAllUnsolvedReports();
    }
}
