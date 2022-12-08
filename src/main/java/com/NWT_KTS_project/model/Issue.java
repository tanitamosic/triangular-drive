package com.NWT_KTS_project.model;

import com.NWT_KTS_project.model.enums.IssueStatus;
import com.NWT_KTS_project.model.users.Admin;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private List<Message> messages;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin")
    private Admin admin;

    @Column(name = "status")
    private IssueStatus status;



}
