package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Report;
import com.NWT_KTS_project.service.ReportService;
import com.NWT_KTS_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    ReportService reportService;

    @GetMapping(value="/block/{usrid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> blockUser(@PathVariable("usrid")Integer userid) {
        try {
            if (userService.blockUser(userid)) {
                return ResponseEntity.ok("User blocked.");
            } else {
                return new ResponseEntity<String>("User not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/get-reports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Report>> getReports() {
        List<Report> reports = reportService.getAllUnsolvedReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping(value="/send-warning/{usrid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sendWarning(@PathVariable("usrid") Integer usrid) {

        // TODO: NEED MAILING SERVICE FROM DEVELOP BRANCH
        return null;
    }

    @GetMapping(value="/solve-report/{repid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> solveReport(@PathVariable("repid") Integer repid) {

        // TODO: NEED MAILING SERVICE FROM DEVELOP BRANCH
        return null;
    }
}
