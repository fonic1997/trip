package com.share.trip.repository;

import com.share.trip.model.Ride;
import com.share.trip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Long> {
    Ride findAllByDriver(User user);
}
