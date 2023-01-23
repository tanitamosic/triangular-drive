package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.DTO.PasswordChangeDTO;
import com.NWT_KTS_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO dto) {
        if (this.userService.changePassword(dto)) {
            return new ResponseEntity<>("Password changed successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Passwords aren't matching.", HttpStatus.BAD_REQUEST);
        }
    }
}