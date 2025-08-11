package com.share.trip.ride.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class RideRequest {

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    @NotNull
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @NotNull
    @Min(value = 1, message = "At least 1 seat must be available")
    private Integer availableSeats;

    private Double price;

    // Getters & setters
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public BigDecimal getPrice() { return BigDecimal.valueOf(price); }
    public void setPrice(Double price) { this.price = price; }
}