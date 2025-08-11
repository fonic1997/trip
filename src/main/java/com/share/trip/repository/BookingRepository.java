package com.share.trip.repository;

import com.share.trip.model.Booking;
import com.share.trip.model.Ride;
import com.share.trip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByPassenger(User passenger);

    List<Booking> findByRide(Ride ride);

    Optional<Booking> findByIdAndRide_DriverId(Long bookingId, Long driverId);
}
