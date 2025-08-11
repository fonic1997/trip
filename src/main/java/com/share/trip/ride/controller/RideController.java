package com.share.trip.ride.controller;


import com.share.trip.ride.dto.RideRequest;
import com.share.trip.ride.dto.RideResponse;
import com.share.trip.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody RideRequest request,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(rideService.createRide(request, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.getRideById(id));
    }

    @GetMapping
    public ResponseEntity<List<RideResponse>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideResponse> updateRide(
            @PathVariable Long id,
            @Valid @RequestBody RideRequest request,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(rideService.updateRide(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(
            @PathVariable Long id,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        rideService.deleteRide(id, userId);
        return ResponseEntity.noContent().build();
    }
}