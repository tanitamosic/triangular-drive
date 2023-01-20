package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.enums.City;
import com.NWT_KTS_project.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Map<String, Object>> getCities() {
        List<Map<String, Object>> retval = new ArrayList<>();
        for (City c: List.of(City.values())) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("code", c);
            obj.put("name", c.name);
            retval.add(obj);
        }
        return retval;
    }
}

