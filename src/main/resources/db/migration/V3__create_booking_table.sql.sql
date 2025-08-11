-- V3__create_bookings_table.sql
-- Migration for Booking table

CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    ride_id BIGINT NOT NULL,
    passenger_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, APPROVED, REJECTED, CANCELLED
    seats_booked INT NOT NULL CHECK (seats_booked > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_booking_ride FOREIGN KEY (ride_id) REFERENCES rides(id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_passenger FOREIGN KEY (passenger_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_booking_status CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED'))
);
