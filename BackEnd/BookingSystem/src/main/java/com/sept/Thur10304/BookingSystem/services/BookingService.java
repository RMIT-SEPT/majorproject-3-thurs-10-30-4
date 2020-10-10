package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.BookingRepository;

import java.sql.Time;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.swing.text.html.Option;

import com.sept.Thur10304.BookingSystem.model.Booking;
import com.sept.Thur10304.BookingSystem.model.Customer;
import com.sept.Thur10304.BookingSystem.services.TimeslotService;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.model.Account;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TimeslotService timeslotService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

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
            // Finds customer (throws exception if not found)
            Customer customer = accountService.findCustomer(customerId);
            // Create booking
            Booking booking = new Booking();
            booking.setTimeslot(timeslot);
            timeslot.setBooking(booking);
            booking.setCustomer(customer);
            bookingRepository.save(booking);
            return booking;
        } else {
            throw new Exception("Timeslot not found");
        }
    }

    public Timeslot getTimeslotByBookingId(Long bookingId) throws Exception{
        Optional<Booking> findBooking = bookingRepository.findById(bookingId);

        if (!findBooking.isPresent()){
            throw new Exception("Booking not found");
        }

        Booking booking = findBooking.get();

        Timeslot timeslot = booking.getTimeslot();

        // CHeck that timeslot is set, probably a redundant check
        if (timeslot == null){
            throw new Exception("Error with service");
        }

        return timeslot;
    }

    public Iterable<Timeslot> getTimeslotByCustomerId(Long customerId) throws Exception{

        // Finds customer (throws exception if not found)
        Customer customer = accountService.findCustomer(customerId);

        Set<Booking> bookings = customer.getBookings();

        Iterator<Booking> bookingIterator = bookings.iterator();

        List<Timeslot> timeslots = new ArrayList(bookings.size());

        while(bookingIterator.hasNext()){
            Booking booking = bookingIterator.next();
            timeslots.add(booking.getTimeslot());
        }

        timeslotService.filterCurrentTimeslots(timeslots);

        return timeslots;
    }

    public boolean deleteBooking(Long bookingId) throws Exception{
        Optional<Booking> findBooking = bookingRepository.findById(bookingId);

        if (!findBooking.isPresent()){
            throw new Exception("Booking not found");
        }

        Booking booking = findBooking.get();

        // Check that booking is at least 48 hours into the future
        // TODO clean up code
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR_OF_DAY, 48);

        // This calendar instance is made to get date from timeslot
        Calendar timeslotTimeDate = Calendar.getInstance();
        timeslotTimeDate.setTime(new Date());
        timeslotTimeDate.setTime(booking.getTimeslot().getDate());
        // This calendar instance is made to get hours from timeslot
        Calendar timeslotTimeHours = Calendar.getInstance();
        timeslotTimeHours.setTime(new Date());
        timeslotTimeHours.setTime(booking.getTimeslot().getStartTime());
        // Combine both calendar instances into one
        Calendar timeslotTime = Calendar.getInstance();
        timeslotTime.setTime(new Date());
        timeslotTime.set(Calendar.YEAR, timeslotTimeDate.get(Calendar.YEAR));
        timeslotTime.set(Calendar.DAY_OF_YEAR, timeslotTimeDate.get(Calendar.DAY_OF_YEAR));
        timeslotTime.set(Calendar.HOUR_OF_DAY, timeslotTimeHours.get(Calendar.HOUR_OF_DAY));

        if (timeslotTime.getTimeInMillis() < c.getTimeInMillis()){
            throw new Exception("Can't cancel less than 48 hours before booking");
        }

        // De-associate timeslot and booking
        booking.getTimeslot().setBooking(null);
        booking.setTimeslot(null);

        // Delete booking
        bookingRepository.delete(findBooking.get());
        return true;

    }
}