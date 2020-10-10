package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.model.Worker;
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

    @Autowired
    private AccountService accountService;

    public Timeslot saveOrUpdateTimeslot(Timeslot timeslot, Long serviceId, Long workerId) throws Exception {
        // TODO check if worker timetable conflicts
        // check for if service exists
        Service_ service = serviceService.getServiceById(Long.toString(serviceId));
        // Check to see if worker exists (throws error if not found)
        Worker worker = accountService.findWorker(workerId);
        // Checks if service exists
        if (service != null
          // Checks that start time is before end time
          // TODO doesn't currently work
          && timeslot.getStartTime().before(timeslot.getEndTime())
          // CHecks that date of timeslot is at least tomorrow 
          && timeslot.getDate().after(new Date())){
            // Set service and save timeslot
            timeslot.setService(service);
            timeslot.setWorker(worker);
            return timeslotRepository.save(timeslot);
        } else {
            throw new Exception("Timeslot is invalid");
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

    public boolean deleteTimeslot(Long timeslotId){
        // Finds the timeslot by its id
        Optional<Timeslot> timeslot = timeslotRepository.findById(timeslotId);

        // If timeslot is found, then delete it and return true, else return false
        if (timeslot.isPresent()){
            timeslotRepository.delete(timeslot.get());
            return true;
        } else {
            return false;
        }
    }

    // Verifies if admin manages service of timeslot
    public boolean verifyIfAdmin(Long timeslotId, Long adminId) throws Exception{
        Timeslot timeslot = getTimeslotById(timeslotId);

        if (timeslot == null){
            throw new Exception("Timeslot not found");
        }

        boolean isAdmin = serviceService.verifyIfAdmin(timeslot.getService().getServiceId(), adminId);

        return isAdmin;
    }
}