package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.BookingRepository;

import java.util.Optional;

import javax.swing.text.html.Option;

import com.sept.Thur10304.BookingSystem.model.Booking;
import com.sept.Thur10304.BookingSystem.services.TimeslotService;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.model.Account;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TimeslotService timeslotService;

    @Autowired
    private AccountRepository accountRepository;

    public Booking saveOrUpdateBooking(Long timeslotId, Long customerId) throws Exception{
        // TODO customer stuff
        // Get timeslot (if exists)
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotId);
        // if present then go on to check if already booked, if not present then error
        if (timeslot != null){
            // If timeslot has booking, then error
            if (timeslot.getBooking() != null){
                throw new Exception("Timeslot already booked");
            }
            // Check if customer exists
            Optional<Account> customer = accountRepository.findById(customerId);
            if (!customer.isPresent()){
                throw new Exception("Customer not found");
            }
            // Create booking
            Booking booking = new Booking();
            booking.setTimeslot(timeslot);
            timeslot.setBooking(booking);
            booking.setCustomer(customer.get());
            bookingRepository.save(booking);
            return booking;
        } else {
            throw new Exception("Timeslot not found");
        }
    }
}