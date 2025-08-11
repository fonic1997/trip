package com.share.trip.ride.dto;
import jakarta.validation.constraints.NotNull;
public class BookingRequest {
    @NotNull(message = "rideId is required")
    private Long rideId;

    public Long getRideId() { return rideId; }
    public void setRideId(Long rideId) { this.rideId = rideId; }
}
