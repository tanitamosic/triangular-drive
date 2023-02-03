package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;
    @GetMapping("/initRideForE2ETest")
    public String initRideForE2ETest() {
        Ride r = testService.createRideForTest();
        return r.getDriver().getName();
    }
}
