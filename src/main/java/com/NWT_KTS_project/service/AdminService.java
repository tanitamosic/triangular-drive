package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.users.Admin;
import com.NWT_KTS_project.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminForIssue(){
        return adminRepository.findAll().get(0);
    }
}
