package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Issue;
import com.NWT_KTS_project.service.IssueService;
import com.NWT_KTS_project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.NWT_KTS_project.DTO.NewDriverDTO;
import com.NWT_KTS_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

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
}
