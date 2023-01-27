package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Report;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.ReportRepository;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    public List<Report> getAllUnsolvedReports() {
        return reportRepository.getAllUnsolvedReports();
    }

    public void solveReport(Integer id) {
        Optional<Report> r = reportRepository.findById(id);
        if (r.isPresent()) {
            Report report = r.get();
            report.setSolved(true);
            reportRepository.saveAndFlush(report);
        }
    }

}
