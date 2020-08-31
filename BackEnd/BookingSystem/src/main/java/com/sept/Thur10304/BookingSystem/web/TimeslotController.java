package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.services.TimeslotService;
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

/**
 * The controller for services
 * Manages the web api for dealing with services
 */
@RestController
@RequestMapping("/api/timeslot")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    // Gets all timeslots for a service
    @GetMapping("/getbyservice/{serviceId}")
    public ResponseEntity<?> getTimeslotByService(@Valid @PathVariable Long serviceId){
        Iterable<Timeslot> timeslots = timeslotService.getAllTimeslotsForService(serviceId);
        if (timeslots != null){
            return new ResponseEntity<Iterable<Timeslot>>(timeslots, HttpStatus.FOUND);
        } else {
            // TODO make error message more clear as to what is wrong
            return new ResponseEntity<String>("Invalid Service Id", HttpStatus.BAD_REQUEST);
        }
    }

    // Gets timeslot by its id
    @GetMapping("/getbyid/{timeslotId}")
    public ResponseEntity<?> getTimeslotById(@Valid @PathVariable Long timeslotId){
        // Retrieves timeslot through service
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotId);
        // If timeslot is found, return it else error
        if (timeslot != null){
            return new ResponseEntity<Timeslot>(timeslot, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<String>("Invalid Timeslot Id", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{timeslotId}")
    public ResponseEntity<?> deleteService(@Valid @PathVariable Long timeslotId) {
        // Run service to delete timeslot from databse
        boolean timeslotDeleted = timeslotService.deleteTimeslot(timeslotId);
        // If timeslot deleted then return true, else false
        if (timeslotDeleted){
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
   