package com.share.trip.service;

import com.share.trip.ride.dto.RideRequest;
import com.share.trip.ride.dto.RideResponse;

import java.util.List;

public interface RideService {
    RideResponse createRide(RideRequest request, Long driverId);
    RideResponse getRideById(Long id);
    List<RideResponse> getAllRides();
    RideResponse updateRide(Long id, RideRequest request, Long driverEmail);
    void deleteRide(Long id, Long driverEmail);
}