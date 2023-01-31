package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Address;
import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PaymentService {

    @Autowired
    private UserRepository userRepository;

    public static final double PRICE_PER_KM = 120;
    private double carTypeStaringPrice(CarType type){
        switch (type){
            case MINIVAN:
                return 180;
            case CARAVAN:
                return 170;
            case VAN:
                return 200;
            case SUV:
                return 190;
            case LIMOUSINE:
                return 250;
            default:
                return 100;
        }
    }

    @Autowired
    private PositionService positionService;
    public boolean processPaymentForRide(ArrayList<Client> clients, ArrayList<Address> route, Car car){
        double distance = positionService.getDistanceForAddresses(route);

        double price = carTypeStaringPrice(car.getType()) + (distance/1000)* PaymentService.PRICE_PER_KM;
        double pricePerClient = price/clients.size();
        for (Client client: clients) {
            if(client.getCreditAvailable() < pricePerClient){
                return false;
            }
            client.setCreditAvailable((float) (client.getCreditAvailable() - pricePerClient));
        }
        return true;
    }

    public float getFunds(int clientId){
        Client user = (Client)userRepository.findById(clientId).get();
        return user.getCreditAvailable();
    }

    public float addFunds(int userId, float amount){
        Client user = (Client)userRepository.findById(userId).get();
        user.setCreditAvailable(user.getCreditAvailable() + amount);
        userRepository.save(user);
        return user.getCreditAvailable();
    }


}
