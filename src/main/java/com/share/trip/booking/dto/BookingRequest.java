package com.share.trip.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingRequest  {

    @NotNull(message = "rideId is required")
    private Long rideId;

    @NotNull(message = "Number of seats is required")
    @Min(value = 1, message = "At least 1 seat must be booked")
    private Integer seats;

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}