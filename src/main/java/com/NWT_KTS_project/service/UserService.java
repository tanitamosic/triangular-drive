package com.NWT_KTS_project.service;


import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param email the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User retUser = userRepository.findUserByEmail(email);
        if (null == retUser) {
            throw new UsernameNotFoundException("User with entered email doesn't exist!");
        }
        return retUser;
    }


    public boolean blockUser(Integer userid) {
        Optional<User> u = userRepository.findById(userid);
        if (u.isPresent()) {
            User user = u.get();
            user.setBlocked(true);
            userRepository.saveAndFlush(user);
        } else {
            return false;
        }
        return true;
    }

}

