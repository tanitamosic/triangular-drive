package com.NWT_KTS_project.service;

import com.NWT_KTS_project.DTO.TokenState;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    UserService userService;

    @Autowired
    TokenUtils tokenUtils;

    public TokenState createToken(User user) {
        String username = user.getUsername();

        String role = user.getRole();
        String jwt = tokenUtils.generateToken(username, role);
        int expiresIn = tokenUtils.getExpiredIn();

        System.out.println(role);
        TokenState token = new TokenState(jwt, expiresIn, role, user);
        token.setLastPasswordResetDate(user.getLastPasswordResetDate() != null ? user.getLastPasswordResetDate().toString() : null);

        return token;
    }

}
