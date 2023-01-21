package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Review;
import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.repository.ReviewRepository;
import com.NWT_KTS_project.repository.RideRepository;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;


    public void sendRideReview(int rideId, int userId, int carRating, int driverRating,String comment) {
        Ride ride = rideRepository.findById(rideId).get();
        User user = userRepository.findById(userId).get();
        Review review = new Review();
        review.setPassenger(user);
        review.setCarRating(carRating);
        review.setDriverRating(driverRating);
        review.setComment(comment);
        ride.getReviews().add(review);
        reviewRepository.save(review);
        rideRepository.save(ride);
    }
}
