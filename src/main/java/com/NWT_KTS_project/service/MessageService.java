package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Issue;
import com.NWT_KTS_project.model.Message;
import com.NWT_KTS_project.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired UserService userService;


    public Issue getCurrentIssueByUserId(int userId){
        return issueService.getIssueByUserId(userId);
    }

    public void sendMessage(int userId, String messageText){
        Issue issue = issueService.getIssueByUserId(userId);
        Message message = new Message();
        message.setMessage(messageText);
        message.setSender(userService.getUserById(userId));
        message.setReceiver(issue.getAdmin());
        message.setTime(LocalDateTime.now());
        message.setIsRead(false);
        issueService.addMessage(issue, message);
        messageRepository.save(message);
    }

    public void sendAdminMessage(int issueId, String messageText){
        Issue issue = issueService.getIssueById(issueId);
        Message message = new Message();
        message.setMessage(messageText);
        message.setSender(issue.getAdmin());
        message.setReceiver(issue.getUser());
        message.setTime(LocalDateTime.now());
        message.setIsRead(false);
        issueService.addMessage(issue, message);
        messageRepository.save(message);
    }
}
