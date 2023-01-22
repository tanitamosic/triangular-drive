package com.NWT_KTS_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
    private Integer userId;
}
