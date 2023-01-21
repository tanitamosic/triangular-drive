package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Issue;
import com.NWT_KTS_project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;



    @GetMapping("/getCurrentIssueMessages/{userId}")
    public Issue getIssue(@PathVariable int userId){
        return messageService.getCurrentIssueByUserId(userId);
    }

    @PostMapping("/sendMessage/{userId}")
    public void SendMessage(@RequestBody String message, @PathVariable int userId){

        messageService.sendMessage(userId, message);

    }
}
