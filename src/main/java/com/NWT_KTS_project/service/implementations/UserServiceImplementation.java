package com.NWT_KTS_project.service.implementations;


import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.UserRepository;
import com.NWT_KTS_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

}

