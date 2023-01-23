package com.NWT_KTS_project.controller;

import com.NWT_KTS_project.dto.*;
import com.NWT_KTS_project.model.enums.City;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.service.LoginService;
import com.NWT_KTS_project.service.MailingService;
import com.NWT_KTS_project.service.RegistrationService;
import com.NWT_KTS_project.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value="/api")
public class RegistrationController {
    @Autowired
    RegistrationService service;
    @Autowired
    MailingService mailingService;


    @PostMapping(value="/register")
    public ResponseEntity<String> beginRegistration(@RequestBody RegistrationDTO dto) {
        Client c = service.registerClient(dto);
        mailingService.sendRegAuthMail(c.getEmail(), c.getConfirmationNumber());
        return ResponseEntity.ok(c.getEmail());
    }

    @PostMapping(value="/register/confirm")
    public ResponseEntity<String> confirmEmail(@RequestBody ConfirmationDTO data){
        if (service.tryConfirmation(data)){
            return ResponseEntity.ok("Confirmation successful.");
        } else {
            return ResponseEntity.of(Optional.of("Confirmation number not matched."));
        }
    }
}
