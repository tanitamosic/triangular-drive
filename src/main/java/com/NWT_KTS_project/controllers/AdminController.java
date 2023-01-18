package com.NWT_KTS_project.controllers;

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
    UserService userService;

    @PostMapping("/register-driver")
    public ResponseEntity<String> registerDriver(@RequestBody NewDriverDTO newDriverDTO) {
        try {
            this.userService.registerDriver(newDriverDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("Driver registration successful!");
    }
}
