package com.NWT_KTS_project.service;

import com.NWT_KTS_project.DTO.ConfirmationDTO;
import com.NWT_KTS_project.DTO.RegistrationDTO;
import com.NWT_KTS_project.model.enums.City;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    UserRepository repository;

    @Autowired
    @Lazy
    PasswordEncoder pe;

    public boolean validate(RegistrationDTO dto){
        //TODO: set conditions
        return true;
    }

    public Client registerClient(RegistrationDTO dto) throws Exception {
        Client c = new Client();
        c.setEmail(dto.email);
        if (!dto.password1.equals(dto.password2) || dto.password1.length() < 6 || dto.password1.length() > 16) {
            throw new Exception("Passwords do not match");
        }
        c.setPassword(pe.encode(dto.password1));
        c.setName(dto.name);
        c.setLastName(dto.lastName);
        c.setPhone(dto.phone);
        c.setCity(dto.city);
        c.setBlocked(false);
        c.setActivated(false);
        c.setCreditAvailable(0f);
        c.setCreditReserved(0f);

        String confNum = String.valueOf(Math.round(Math.random()*1000000));
        c.setConfirmationNumber(confNum);
        repository.save(c);
        return c;
    }

    public boolean tryConfirmation(ConfirmationDTO data){
        Client c = (Client)repository.findUserByEmail(data.email);
        boolean res = data.confNum.equals(c.getConfirmationNumber());
        if (res){
            c.setActivated(true);
            repository.saveAndFlush(c);
        }
        return res;
    }
}
