package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Address;
import com.NWT_KTS_project.model.enums.City;
import com.NWT_KTS_project.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;


    public ArrayList<Address> getAddressesFromString(String stops){
        ArrayList<Address> addresses = new ArrayList<Address>();
        for(String address: stops.split(";")){
            Address a = new Address();
            a.setCity(City.valueOf(address.split(",")[0]));
            a.setStreet(address.split(",")[1]);
            a.setNumber(address.split(",")[2]);
            a.setLatitude(Double.parseDouble(address.split(",")[3]));
            a.setLongitude(Double.parseDouble(address.split(",")[4]));
            addresses.add(a);
        }

        return addresses;
    }
}

