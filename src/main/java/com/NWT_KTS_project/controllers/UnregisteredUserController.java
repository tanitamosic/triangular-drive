package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unregisteredUser")
public class UnregisteredUserController {

    @Autowired
    private UserService userService;
    @GetMapping(value="/user-profile/{usrid}")
    public ResponseEntity<User> fetchUser(@PathVariable("usrid") Integer userid) {
        User u = userService.loadUserById(userid);
        if (null == u) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<User>(u, HttpStatus.OK);
    }
}
