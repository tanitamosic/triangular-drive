package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Issue;
import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.NWT_KTS_project.DTO.NewDriverDTO;
import com.NWT_KTS_project.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MailingService mailingService;

    @GetMapping("/getIssues/{adminId}")
    public List<Issue> getActiveIssues(@PathVariable int adminId){
        return issueService.getIssuesByAdminId(adminId);
    }

    @PostMapping("/closeIssue/{issueId}")
    public void closeIssue(@PathVariable int issueId){
        issueService.closeIssue(issueId);
    }

    @PostMapping("/sendMessage/{issueId}")
    public void sendMessage( @PathVariable int issueId,@RequestBody String message){
        messageService.sendAdminMessage( issueId, message);
    }

    @PostMapping("/register-driver")
    public ResponseEntity<String> registerDriver(@RequestBody NewDriverDTO newDriverDTO) {
        try {
            if (this.userService.registerDriver(newDriverDTO)) {
                return ResponseEntity.ok("Driver registration successful!");
            } else {
                return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Something went wromg, sowwy uwu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

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

        User u = userService.getUserById(usrid);
        if (null == u) {
            return new ResponseEntity<>("No such user", HttpStatus.BAD_REQUEST);
        }
        mailingService.sendWarning(u.getEmail());
        return new ResponseEntity<>("User warned.", HttpStatus.OK);
    }

    @GetMapping(value="/solve-report/{repid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> solveReport(@PathVariable("repid") Integer repid) {

        reportService.solveReport(repid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-total-income/{date1}/{date2}")
    public ResponseEntity<Integer> getTotalDriverIncome(@PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-avg-income/{date1}/{date2}")
    public ResponseEntity<Integer> getAvgDriverIncome(@PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-total-distance/{date1}/{date2}")
    public ResponseEntity<Integer> getTotalDriverDistance(@PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-avg-distance/{date1}/{date2}")
    public ResponseEntity<Integer> getAvgDriverDistance(@PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-total-ridecount/{date1}/{date2}")
    public ResponseEntity<Integer> getTotalDriverRidecount(@PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-avg-ridecount/{date1}/{date2}")
    public ResponseEntity<Integer> getAvgDriverRidecount(@PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        return ResponseEntity.ok().build();
    }

}
