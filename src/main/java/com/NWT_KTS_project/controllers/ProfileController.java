package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.DTO.LoggedUserDTO;
import com.NWT_KTS_project.model.DriverUpdateRequest;
import com.NWT_KTS_project.model.Photo;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.service.AddressService;
import com.NWT_KTS_project.service.DriverService;
import com.NWT_KTS_project.service.MailingService;
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

    @Autowired
    MailingService mailingService;

    @Autowired
    DriverService driverService;

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
        try {
            if (ludto.role.equals("ROLE_DRIVER")) {
                Driver d = (Driver) userService.getUserById(ludto.getId());
                DriverUpdateRequest dur = driverService.createDriverUpdateRequest(ludto);
                mailingService.sendDriverUpdateRequest(d, dur);
                return new ResponseEntity<>("Profile update request sent to admins.", HttpStatus.OK);
            } else {
                if (userService.updateUserMetadata(ludto)) {
                    return new ResponseEntity<>("Profile successfully updated.", HttpStatus.OK);
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value="/get-cities")
    public ResponseEntity<List<Map<String, Object>>> getCities() {
        return new ResponseEntity<>(addressService.getCities(), HttpStatus.OK);
    }

    @GetMapping(value="accept-pending-profile-update/{requestId}")
    public ResponseEntity<String> acceptRequest(@PathVariable("requestId") Integer id) {
        DriverUpdateRequest dur = driverService.getDriverUpdateRequest(id);
        if (null == dur) {
            return new ResponseEntity<>("Stale link.", HttpStatus.OK);
        }
        driverService.acceptDriverUpdate(dur);
        return new ResponseEntity<>("Driver profile updated", HttpStatus.OK);
    }

    @GetMapping(value="reject-pending-profile-update/{requestId}")
    public ResponseEntity<String> rejectRequest(@PathVariable("requestId") Integer id) {
        DriverUpdateRequest dur = driverService.getDriverUpdateRequest(id);
        if (null == dur) {
            return new ResponseEntity<>("Stale link.", HttpStatus.OK);
        }
        driverService.rejectDriverUpdate(dur);
        return new ResponseEntity<>("Driver profile updated", HttpStatus.OK);
    }
}
