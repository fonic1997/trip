package com.share.trip.service;

import com.share.trip.common.exception.BadRequestException;
import com.share.trip.common.exception.ResourceNotFoundException;
import com.share.trip.common.exception.UnauthorizedActionException;
import com.share.trip.model.Booking;
import com.share.trip.model.Ride;
import com.share.trip.model.User;
import com.share.trip.repository.BookingRepository;
import com.share.trip.repository.RideRepository;
import com.share.trip.repository.UserRepository;
import com.share.trip.booking.dto.BookingRequest;
import com.share.trip.booking.dto.BookingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional

public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RideRepository rideRepository,
                              UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }
    @Override
    public BookingResponse createBooking(Long passengerId, BookingRequest request) {
        User passenger = userRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        if (!"PASSENGER".equalsIgnoreCase(passenger.getRole())) {
            throw new UnauthorizedActionException("Only passengers can book rides");
        }

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        // Validate seat availability
        if (ride.getAvailableSeats() < request.getSeats()) {
            throw new IllegalArgumentException("Not enough seats available for this ride");
        }

        // Reduce available seats
        ride.setAvailableSeats(ride.getAvailableSeats() - request.getSeats());
        rideRepository.save(ride);

        // Create booking
        Booking booking = new Booking();
        booking.setPassenger(passenger);
        booking.setRide(ride);
        booking.setSeatsBooked(request.getSeats());
        booking.setStatus(Booking.Status.PENDING);

        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    @Override
    public BookingResponse approveBooking(Long bookingId, Long driverId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Ride ride = booking.getRide();

        if (!ride.getDriver().getId().equals(driverId)) {
            throw new UnauthorizedActionException("Only ride driver can approve booking");
        }

        if (booking.getStatus() != Booking.Status.PENDING) {
            throw new BadRequestException("Booking is already " + booking.getStatus());
        }

        if (ride.getAvailableSeats() == null || ride.getAvailableSeats() <= 0) {
            throw new BadRequestException("No available seats left for this ride");
        }

        // Reduce available seats by 1
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        // Save the updated ride
        rideRepository.save(ride);

        booking.setStatus(Booking.Status.APPROVED);
        Booking updated = bookingRepository.save(booking);

        return mapToResponse(updated);
    }

    @Override
    public List<BookingResponse> getBookingsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Booking> bookings;

        if ("DRIVER".equalsIgnoreCase(user.getRole())) {
            bookings = bookingRepository.findByRide(rideRepository.findAllByDriver(user));
            // Note: You may want to write a custom repo method to get all bookings for rides owned by driver
        } else {
            bookings = bookingRepository.findByPassenger(user);
        }

        return bookings.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private BookingResponse mapToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getRide().getId(),
                booking.getPassenger().getId(),
                booking.getStatus().name(),
                booking.getCreatedAt()
        );
    }
}
