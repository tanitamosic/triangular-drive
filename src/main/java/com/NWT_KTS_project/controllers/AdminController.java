package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Issue;
import com.NWT_KTS_project.service.IssueService;
import com.NWT_KTS_project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MessageService messageService;

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

}
