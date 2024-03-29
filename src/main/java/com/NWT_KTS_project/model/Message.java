package com.NWT_KTS_project.model;

import com.NWT_KTS_project.DTO.MessageDTO;
import com.NWT_KTS_project.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "time")
    private LocalDateTime time;

    public Message(MessageDTO dto, User s, User r, LocalDateTime dateTime) {
        this.sender = s;
        this.receiver = r;
        this.message = dto.getMessage();
        this.isRead = dto.getIsRead();
        this.time = dateTime;
    }

}
