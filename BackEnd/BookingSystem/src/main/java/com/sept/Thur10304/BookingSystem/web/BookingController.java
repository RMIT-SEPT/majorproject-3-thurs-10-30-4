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
import com.fasterxml.jackson.annotation.*;

import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    public class BookingForm{

        private Long timeslotId;

        private Long customerId;

        public BookingForm(){}

        public Long getTimeslotId(){
            return timeslotId;
        }

        public void setTimeslotId(Long timeslotId){
            this.timeslotId = timeslotId;
        }

        public Long getCustomertId(){
            return customerId;
        }

        public void setCustomerId(Long customerId){
            this.customerId = customerId;
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createNewBooking(@Valid @RequestBody Map<String, Long> jsonInput, BindingResult result) {
        // TODO customer stuff
        if (result.hasErrors()){
            return new ResponseEntity<String>("Request form is not in correct format", HttpStatus.BAD_REQUEST);
        }
        // Attempts to make booking
        try {
            Long timeslotId = jsonInput.get("timeslotId");
            Long customerId = jsonInput.get("customerId");
            Booking booking = bookingService.saveOrUpdateBooking(timeslotId, customerId);
            // If no exceptions thrown, then return timeslot (includes booking)
            return new ResponseEntity<Timeslot>(booking.getTimeslot(), HttpStatus.CREATED);
        } catch (Exception e){
            // If error thrown, return error message
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getbyid/{bookingId}")
    public ResponseEntity<?> getTimeslotByBookingId(@Valid @PathVariable Long bookingId){
        try{
            Timeslot timeslot = bookingService.getTimeslotByBookingId(bookingId);
            return new ResponseEntity<Timeslot>(timeslot, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<?> deleteBooking(@Valid @PathVariable Long bookingId) {
        try{
            boolean bookingDeleted = bookingService.deleteBooking(bookingId);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}