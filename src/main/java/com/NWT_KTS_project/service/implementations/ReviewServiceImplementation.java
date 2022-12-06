package com.NWT_KTS_project.service.implementations;

import com.NWT_KTS_project.repository.ReviewRepository;
import com.NWT_KTS_project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImplementation implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
}
