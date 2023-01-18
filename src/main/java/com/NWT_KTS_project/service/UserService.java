package com.NWT_KTS_project.service;


import com.NWT_KTS_project.DTO.NewDriverDTO;
import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.CarRepository;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    @Lazy
    private PasswordEncoder pe;


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

    public void registerDriver(NewDriverDTO newDriverDTO) {
        Driver driver = new Driver();
        Car c = newDriverDTO.getCar();
        carRepository.save(c);
        driver.setCar(newDriverDTO.getCar());

        driver.setEmail(newDriverDTO.getEmail());
        driver.setName(newDriverDTO.getName());
        driver.setLastName(newDriverDTO.getLastName());
        driver.setPhone(newDriverDTO.getPhone());
        driver.setCity(newDriverDTO.getCity());

        String encodedPassword = pe.encode(newDriverDTO.getPassword());
        driver.setPassword(encodedPassword);

        driver.setScore(0.0f);
        driver.setBlocked(false);
        driver.setActivated(false);
        driver.setStatus(DriverStatus.OFFLINE);
        driver.setLastPasswordResetDate(Timestamp.from(Instant.now()));
        userRepository.save(driver);
    }


}

