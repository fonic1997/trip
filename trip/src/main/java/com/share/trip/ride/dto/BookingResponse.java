package com.share.trip.ride.dto;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private Long rideId;
    private Long passengerId;
    private String status;
    private LocalDateTime createdAt;

    public BookingResponse() {}

    public BookingResponse(Long id, Long rideId, Long passengerId, String status, LocalDateTime createdAt) {
        this.id = id;
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRideId() { return rideId; }
    public void setRideId(Long rideId) { this.rideId = rideId; }

    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
