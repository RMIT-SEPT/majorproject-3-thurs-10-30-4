package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.services.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * The controller for services
 * Manages the web api for dealing with services
 */
@RestController
@RequestMapping("/api/timeslot")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    // Saves a timeslot to the database
    @PostMapping("/save/{serviceId}")
    public ResponseEntity<?> createNewTimeslot(@Valid @RequestBody Timeslot timeslot, @PathVariable Long serviceId, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<String>("Invalid Timeslot Object", HttpStatus.BAD_REQUEST);
        }

        Timeslot timeslot1 = timeslotService.saveOrUpdateTimeslot(timeslot, serviceId);
        if (timeslot1 != null){
            return new ResponseEntity<Timeslot>(timeslot1, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Invalid Timeslot Object", HttpStatus.BAD_REQUEST);
        }
    }
}
   