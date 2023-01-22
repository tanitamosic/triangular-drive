package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.DTO.LoggedUserDTO;
import com.NWT_KTS_project.model.Photo;
import com.NWT_KTS_project.service.AddressService;
import com.NWT_KTS_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;

    @PostMapping(value="/upload-profile-picture", consumes = "multipart/form-data")
    public ResponseEntity<Photo> uploadProfilePicture(@RequestParam("file") MultipartFile multipartFile) {
        try {
            Photo photo = userService.saveImage(multipartFile);
            return new ResponseEntity<>(photo, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value="/update")
    public ResponseEntity<String> updateProfile(@RequestBody LoggedUserDTO ludto) {
        if (ludto.role.equals("ROLE_DRIVER")) {
            // TODO: SEND UPDATE APPROVAL REQUEST TO ADMIN
        } else {
            if (userService.updateUserMetadata(ludto)) {
                return new ResponseEntity<>("Profile successfully updated.", HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }

        return null;
    }

    @GetMapping(value="/get-cities")
    public ResponseEntity<List<Map<String, Object>>> getCities() {
        return new ResponseEntity<>(addressService.getCities(), HttpStatus.OK);
    }
}
