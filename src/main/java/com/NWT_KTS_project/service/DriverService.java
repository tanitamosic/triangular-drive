package com.NWT_KTS_project.service;

import com.NWT_KTS_project.DTO.UserDTO;
import com.NWT_KTS_project.model.DriverUpdateRequest;
import com.NWT_KTS_project.model.Position;
import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.repository.DriverUpdateRepository;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class DriverService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DriverUpdateRepository driverUpdateRepository;


    public void setDriverStatus(int driverId, DriverStatus status) {
        Driver driver = (Driver) userRepository.findById(driverId).get();
        driver.setStatus(status);
        userRepository.save(driver);
    }


    public Driver getAvailableDriver(double latitude, double longitude, boolean petFriendly, boolean babyFriendly, int numberOfPassengers, CarType carType) {
        HashMap<Integer, Position> drivers = (HashMap<Integer, Position>) positionService.getPositions();
        Position clientPosition = new Position(latitude, longitude);
        ArrayList<Driver> availableDrivers = new ArrayList<Driver>();
        for(Integer driverId : drivers.keySet()){
            Position driverPos = drivers.get(driverId);
            if(positionService.getDistance(clientPosition,driverPos)< PositionService.MAX_DISTANCE){
                Driver driver = (Driver) userRepository.findById(driverId).get();
                if(driver.getStatus() == DriverStatus.AVAILABLE && driver.getCar().getSeats() >= numberOfPassengers && driver.getCar().getType()== carType){
                    if (petFriendly && !driver.getCar().getPetFriendly()) continue;
                    if (babyFriendly && !driver.getCar().getBabyFriendly()) continue;
                    availableDrivers.add(driver);
                }
            }
        }
        if(availableDrivers.size() == 0) return null;

        Driver closestDriver = availableDrivers.get(0);
        double minDistance = positionService.getDistance(clientPosition, drivers.get(closestDriver.getId()));
        for(Driver driver : availableDrivers){
            double distance = positionService.getDistance(clientPosition, drivers.get(driver.getId()));
            if(distance < minDistance){
                minDistance = distance;
                closestDriver = driver;
            }
        }

        return closestDriver;
    }

    public DriverStatus getDriverStatus(Integer id) {
        Driver driver = (Driver) userRepository.findById(id).get();
        return driver.getStatus();
    }

    public DriverUpdateRequest createDriverUpdateRequest(UserDTO newProfileInfo) {
        DriverUpdateRequest dur = new DriverUpdateRequest();
        dur.setDriverId(newProfileInfo.getId());
        dur.setEmail(newProfileInfo.getEmail());
        dur.setCity(newProfileInfo.getCity());
        dur.setName(newProfileInfo.getName());
        dur.setLastName(newProfileInfo.getLastName());
        dur.setPhone(newProfileInfo.getPhone());
        dur.setPhoto(newProfileInfo.getPhoto());
        dur.setApproved(false);
        dur.setPending(true);
        driverUpdateRepository.saveAndFlush(dur);
        return dur;
    }

    public DriverUpdateRequest getDriverUpdateRequest(Integer id) {
        DriverUpdateRequest dur = driverUpdateRepository.findById(id).get();
        if (!dur.isPending()) {
            return null;
        } else {
            return dur;
        }
    }

    public void acceptDriverUpdate(DriverUpdateRequest dur) {
        Driver driver = (Driver) userRepository.findById(dur.getDriverId()).get();
        driver.setCity(dur.getCity());
        driver.setPhone(dur.getPhone());
        driver.setName(dur.getName());
        driver.setLastName(dur.getLastName());
        driver.setEmail(dur.getEmail());
        driver.setPhoto(dur.getPhoto());
        userRepository.saveAndFlush(driver);

        dur.setPending(false);
        dur.setApproved(true);
        driverUpdateRepository.saveAndFlush(dur);
    }

    public void rejectDriverUpdate(DriverUpdateRequest dur) {
        dur.setPending(false);
        dur.setApproved(false);
        driverUpdateRepository.saveAndFlush(dur);
    }
}
