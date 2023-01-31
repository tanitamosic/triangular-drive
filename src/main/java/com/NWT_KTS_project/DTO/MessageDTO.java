package com.NWT_KTS_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Integer issueId;
    private Integer sender;
    private Integer receiver;
    private String message;
    private Boolean isRead;
    private String time;
}
