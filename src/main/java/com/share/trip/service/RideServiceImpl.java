package com.share.trip.service;

import com.share.trip.common.exception.ResourceNotFoundException;
import com.share.trip.common.exception.UnauthorizedActionException;
import com.share.trip.model.Ride;
import com.share.trip.model.User;
import com.share.trip.repository.RideRepository;
import com.share.trip.repository.UserRepository;
import com.share.trip.ride.dto.RideRequest;
import com.share.trip.ride.dto.RideResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public RideServiceImpl(RideRepository rideRepository, UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RideResponse createRide(RideRequest request, Long driverId) {
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!"DRIVER".equalsIgnoreCase(driver.getRole())) {
            throw new UnauthorizedActionException("Only drivers can create rides");
        }

        Ride ride = new Ride();
        ride.setDriver(driver);
        ride.setOrigin(request.getOrigin());
        ride.setDestination(request.getDestination());
        ride.setDepartureTime(request.getDepartureTime());
        ride.setAvailableSeats(request.getAvailableSeats());
        ride.setPrice(request.getPrice());

        Ride savedRide = rideRepository.save(ride);
        return mapToResponse(savedRide);
    }

    @Override
    public RideResponse getRideById(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
        return mapToResponse(ride);
    }

    @Override
    public List<RideResponse> getAllRides() {
        return rideRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RideResponse updateRide(Long id, RideRequest request, Long userId) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!ride.getDriver().getId().equals(userId)) {
            throw new UnauthorizedActionException("You can only update your own rides");
        }

        ride.setOrigin(request.getOrigin());
        ride.setDestination(request.getDestination());
        ride.setDepartureTime(request.getDepartureTime());
        ride.setAvailableSeats(request.getAvailableSeats());
        ride.setPrice(request.getPrice());

        return mapToResponse(rideRepository.save(ride));
    }

    @Override
    public void deleteRide(Long id, Long userId) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!ride.getDriver().getId().equals(userId)) {
            throw new UnauthorizedActionException("You can only delete your own rides");
        }
        else {
            rideRepository.delete(ride);
        }
    }

    private RideResponse mapToResponse(Ride ride) {
        RideResponse response = new RideResponse();
        response.setId(ride.getId());
        response.setDriverName(ride.getDriver().getFullName());
        response.setOrigin(ride.getOrigin());
        response.setDestination(ride.getDestination());
        response.setDepartureTime(ride.getDepartureTime());
        response.setAvailableSeats(ride.getAvailableSeats());
        response.setPrice(ride.getPrice());
        return response;
    }
}