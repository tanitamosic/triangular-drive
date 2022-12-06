package com.NWT_KTS_project.controller;

import com.NWT_KTS_project.DTO.CredentialsDTO;
import com.NWT_KTS_project.DTO.TokenState;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.service.LoginService;
import com.NWT_KTS_project.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    TokenService tokenService;


    @PostMapping(value="/login")
    public ResponseEntity<TokenState> attemptLogin(@RequestBody CredentialsDTO dto) {
        String email = dto.email;
        String password = dto.password;
        User u = loginService.attemptLogin(email, password);

        if (null == u) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TokenState token = tokenService.createToken(u);
        return ResponseEntity.ok(token);
    }
}
