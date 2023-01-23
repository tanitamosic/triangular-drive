package com.NWT_KTS_project.service;

import com.NWT_KTS_project.dto.ConfirmationDTO;
import com.NWT_KTS_project.dto.RegistrationDTO;
import com.NWT_KTS_project.model.enums.City;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    UserRepository repository;

    public boolean validate(RegistrationDTO dto){
        //TODO: set conditions
        return true;
    }

    public Client registerClient(RegistrationDTO dto){
        Client c = new Client();
        c.setEmail(dto.email);
        c.setPassword(new BCryptPasswordEncoder().encode(dto.password));
        c.setName(dto.name);
        c.setLastName(dto.lastName);
        c.setPhone(dto.phone);
        c.setCity(City.fromString(dto.city));
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
        }
        return res;
    }
}
