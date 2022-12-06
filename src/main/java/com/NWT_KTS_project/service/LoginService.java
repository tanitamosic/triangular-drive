package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public User attemptLogin(String email, String password) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User u = (User) authentication.getPrincipal();

        return u.getActivated() && !u.getBlocked() ? u : null;
    }
}
