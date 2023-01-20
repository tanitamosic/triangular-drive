package com.NWT_KTS_project.service;


import com.NWT_KTS_project.DTO.LoggedUserDTO;
import com.NWT_KTS_project.model.Photo;
import com.NWT_KTS_project.model.users.Admin;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.PhotoRepository;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {

    private static final String DESTINATION = "./frontend/src/assets/profile-images/";

    @Autowired
    private PhotoRepository photoRepository;
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


    public boolean updateUserMetadata(LoggedUserDTO ludto) {
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
}

