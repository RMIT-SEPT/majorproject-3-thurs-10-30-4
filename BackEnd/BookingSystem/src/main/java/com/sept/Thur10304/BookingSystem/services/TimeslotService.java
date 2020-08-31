package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.repositories.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sept.Thur10304.BookingSystem.model.Service_;
import java.util.Date;
import java.util.Optional;

@Service
public class TimeslotService {

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private Service_Service serviceService;

    public Timeslot saveOrUpdateTimeslot(Timeslot timeslot, Long serviceId) {
        // TODO check if worker timetable conflicts
        // check for if service exists
        Service_ service = serviceService.getServiceById(Long.toString(serviceId));
        // Checks if service exists
        if (service != null
          // Checks that start time is before end time
          // TODO doesn't currently work
          && timeslot.getStartTime().before(timeslot.getEndTime())
          // CHecks that date of timeslot is at least tomorrow 
          && timeslot.getDate().after(new Date())){
            // Set service and save timeslot
            timeslot.setService(service);
            return timeslotRepository.save(timeslot);
        } else {
            return null;
        }
    }

    public Iterable<Timeslot> getAllTimeslotsForService(Long serviceId){
        // Gets service from database
        Service_ service = serviceService.getServiceById(Long.toString(serviceId));
        // If service found then return all found timeslots (or empty iterable if none)
        // If no service found then return null
        if (service != null){
            // Finds timeslots that are registered with that service
            Iterable<Timeslot> timeslots = timeslotRepository.findByService(service);
            // Returns timeslots found (if any)
            return timeslots;
        } else {
            return null;
        }
    }

    public Timeslot getTimeslotById(Long timeslotId){
        // Find timeslot by id
        Optional<Timeslot> timeslot = timeslotRepository.findById(timeslotId);
        // If timeslot is present then return it else false
        if (timeslot.isPresent()){
            return timeslot.get();
        } else {
            return null;
        }
    }
}