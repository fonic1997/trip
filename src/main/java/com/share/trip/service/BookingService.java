package com.share.trip.service;

import com.share.trip.ride.dto.BookingRequest;
import com.share.trip.ride.dto.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(Long passengerId, BookingRequest request);

    BookingResponse approveBooking(Long bookingId, Long driverId);

    List<BookingResponse> getBookingsForUser(Long userId);
}
