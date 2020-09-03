package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.services.BookingService;
import com.sept.Thur10304.BookingSystem.model.Booking;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/save/{timeslotId}")
    public ResponseEntity<?> createNewBooking(@Valid @PathVariable Long timeslotId) {
        // TODO customer stuff
        // Attempts to make booking
        try {
            Booking booking = bookingService.saveOrUpdateBooking(timeslotId);
            // If no exceptions thrown, then return timeslot (includes booking)
            return new ResponseEntity<Timeslot>(booking.getTimeslot(), HttpStatus.CREATED);
        } catch (Exception e){
            // If error thrown, return error message
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    
}