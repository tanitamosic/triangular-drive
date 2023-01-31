package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Issue;
import com.NWT_KTS_project.model.Message;
import com.NWT_KTS_project.model.enums.IssueStatus;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserService userService;

    @Autowired AdminService adminService;

    public Issue getIssueById(int id){
        return issueRepository.findById(id).get();
    }

    public Issue getIssueByUserId(int userId){
        List<Issue> issues = issueRepository.findByUser_Id(userId);
        for (Issue issue: issues) {
            if(issue.getStatus() == IssueStatus.OPEN){
                return issue;
            }
        }
        Issue issue = new Issue();
        issue.setUser(userService.getUserById(userId));
        issue.setAdmin(adminService.getAdminForIssue());
        issue.setStatus(IssueStatus.OPEN);
        issue.setMessages(Collections.emptyList());
        issueRepository.save(issue);

        return issue;
    }

    public void addMessage(Issue issue, Message message){
        issue.getMessages().add(message);
        issueRepository.save(issue);
    }

    public void closeIssue(int issueId){
        Issue issue = issueRepository.findById(issueId).get();
        issue.setStatus(IssueStatus.CLOSED);
        issueRepository.save(issue);
    }

    public List<Issue> getIssuesByAdminId(int adminId){
        return issueRepository.findByAdmin_Id(adminId);
    }

    public User getReceiver(Integer id) {
        return userService.loadUserById(id);
    }

    public User getSender(Integer id) {
        return userService.loadUserById(id);
    }

}
