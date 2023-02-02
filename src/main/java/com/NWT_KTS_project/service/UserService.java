package com.NWT_KTS_project.service;


import com.NWT_KTS_project.model.Route;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.DTO.UserDTO;
import com.NWT_KTS_project.DTO.PasswordChangeDTO;
import com.NWT_KTS_project.model.Photo;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.PhotoRepository;
import com.NWT_KTS_project.DTO.NewDriverDTO;
import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.repository.CarRepository;
import com.NWT_KTS_project.repository.RouteRepository;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.util.ArrayList;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UserService implements UserDetailsService {

    private static final String DESTINATION = "./frontend/src/assets/profile-images/";

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    @Lazy
    private PasswordEncoder pe;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private MailingService mailingService;


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

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }
    public boolean registerDriver(NewDriverDTO newDriverDTO) {
        Driver driver = new Driver();
        Car c = newDriverDTO.getCar();
        carRepository.save(c);
        driver.setCar(newDriverDTO.getCar());

        driver.setEmail(newDriverDTO.getEmail());
        driver.setName(newDriverDTO.getName());
        driver.setLastName(newDriverDTO.getLastName());
        driver.setPhone(newDriverDTO.getPhone());
        driver.setCity(newDriverDTO.getCity());

        String password1 = newDriverDTO.getPassword1();
        String password2 = newDriverDTO.getPassword2();
        if (password1.equals(password2) && password2.length() <= 16 && password2.length() >= 6) {
            String encodedPassword = pe.encode(newDriverDTO.getPassword1());
            driver.setPassword(encodedPassword);
        } else {
            return false;
        }

        driver.setScore(0.0f);
        driver.setBlocked(false);
        driver.setActivated(true);
        driver.setStatus(DriverStatus.OFFLINE);
        driver.setLastPasswordResetDate(Timestamp.from(Instant.now()));
        userRepository.save(driver);

        mailingService.sendRegAcceptMail(driver.getEmail());
        return true;
    }


    public ArrayList<Client> getClientsFromPassangersString(String passangers){
        ArrayList<Client> users = new ArrayList<Client>();

        for (String passanger : passangers.split(",")) {
            User user = userRepository.findUserByEmail(passanger);
            if (user != null) {
                users.add((Client) user);
            }
        }

        return users;
    }
    
    public User loadUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }


    public String writeFile(MultipartFile multipartFile) throws IOException {
        String fileName = generateRandomFileName();
        byte[] file = multipartFile.getBytes();
        Path path = Paths.get(DESTINATION + fileName);
        Files.createFile(path);
        Files.write(path, file);

        return "assets/profile-images/".concat(fileName);
    }
    public Photo saveImage(MultipartFile multipartFile) throws IOException {
        Photo photo = new Photo();
        String filepathForDB = writeFile(multipartFile);
        photo.setPath(filepathForDB);
        photoRepository.saveAndFlush(photo);
        return photo;
    }
    private String generateRandomFileName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 18;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString() + ".jpg";
    }


    public boolean updateUserMetadata(UserDTO ludto) {
        Optional<User> user = userRepository.findById(ludto.id);
        if (user.isPresent()) {
            User u = user.get();
            u.setCity(ludto.getCity());
            u.setEmail(ludto.getEmail());
            u.setName(ludto.getName());
            u.setLastName(ludto.getLastName());
            u.setPhone(ludto.getPhone());
            u.setPhoto(ludto.getPhoto());
            userRepository.saveAndFlush(u);
            return true;
        } else {
            return false;
        }
    }

    public boolean changePassword(PasswordChangeDTO dto) {
        String oldPassword = dto.getOldPassword();
        String newPassword1 = dto.getNewPassword1();
        String newPassword2 = dto.getNewPassword2();
        Integer id = dto.getUserId();
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            User user = u.get();
            if (newPassword1.equals(newPassword2) && newPassword1.length() >= 6 && newPassword1.length() <= 16) {
                if (pe.matches(oldPassword, user.getPassword())) {
                    String newPassword = pe.encode(newPassword1);
                    user.setPassword(newPassword);
                    userRepository.saveAndFlush(user);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
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

    public void resetPassword(String email) {
        User u = userRepository.findUserByEmail(email);
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String newPassword = buffer.toString();
        String encodedPassword = pe.encode(newPassword);
        u.setPassword(encodedPassword);
        userRepository.saveAndFlush(u);

        mailingService.sendPasswordResetMail(email, newPassword);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void favoriteRoute(Integer userId, Integer routeId){
        Optional<User> u = userRepository.findById(userId);
        Client client = (Client) u.get();
        Route route = routeRepository.findById(routeId).get();
        

    }
}

