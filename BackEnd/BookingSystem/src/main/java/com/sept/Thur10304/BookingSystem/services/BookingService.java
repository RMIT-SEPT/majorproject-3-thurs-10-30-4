package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.BookingRepository;

import java.util.Optional;

import com.sept.Thur10304.BookingSystem.model.Booking;
import com.sept.Thur10304.BookingSystem.services.TimeslotService;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TimeslotService timeslotService;

    public Booking saveOrUpdateBooking(Long timeslotId) throws Exception{
        // TODO customer stuff
        // Get timeslot (if exists)
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotId);
        // if present then go on to check if already booked, if not present then error
        if (timeslot != null){
            // If timeslot has booking, then error
            if (timeslot.getBooking() != null){
                throw new Exception("Timeslot already booked");
            }
            // Create booking
            Booking booking = new Booking();
            booking.setTimeslot(timeslot);
            timeslot.setBooking(booking);
            bookingRepository.save(booking);
            return booking;
        } else {
            throw new Exception("Timeslot not found");
        }
    }
}