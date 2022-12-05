package com.NWT_KTS_project.service.implementations;

import com.NWT_KTS_project.repository.AddressRepository;
import com.NWT_KTS_project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplementation implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
}

