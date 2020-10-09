package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.model.Worker;
import com.sept.Thur10304.BookingSystem.repositories.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Service_;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Calendar tommorow = Calendar.getInstance();
        tommorow.add(Calendar.DATE, 1);
        // Checks if service exists
        if (service == null){
            throw new Exception("Service not found");

        // Checks that start time is before end time
        } else if (!timeslot.getStartTime().before(timeslot.getEndTime())){
            throw new Exception("Start time must be before end time");

        // CHecks that date of timeslot is at least tomorrow 
        } else if (timeslot.getDate().getTime() < tommorow.getTimeInMillis()){
            throw new Exception("Date must be after today");
        } else {
            // Set service and save timeslot
            timeslot.setService(service);
            timeslot.setWorker(worker);
            return timeslotRepository.save(timeslot);
        }
    }

    public Set<Timeslot> getAllTimeslotsForService(Long serviceId){
        // Gets service from database
        Service_ service = serviceService.getServiceById(Long.toString(serviceId));
        // If service found then return all found timeslots (or empty iterable if none)
        // If no service found then return null
        if (service != null){
            // Finds timeslots that are registered with that service
            Set<Timeslot> timeslots = service.getTimeslots();
            // Filters out timeslots that have passed
            filterCurrentTimeslots(timeslots);
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

    public Set<Timeslot> getTimeslotsByWorkerId(Long workerId) throws Exception{
        Worker worker = accountService.findWorker(workerId);
        Set<Timeslot> timeslots = worker.getAssignedTimeslots();
        // Filter to current timeslots (ones that have yet to end)
        filterCurrentTimeslots(timeslots);
        return timeslots;
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

    public Set<Timeslot> getTimeslotsByAdmin(Long adminId) throws Exception{
        Admin admin = accountService.findAdmin(adminId);
        if (admin.getService() == null){
            throw new Exception("Admin doesn't have a service");
        }
        Set<Timeslot> timeslots = admin.getService().getTimeslots();
        // Filter timeslots to current timeslots
        filterCurrentTimeslots(timeslots);
        return timeslots;
    }

    // Removes all timeslots that have ended
    public Collection<Timeslot> filterCurrentTimeslots(Collection<Timeslot> timeslots){
        for (Timeslot timeslot : timeslots){
            Calendar timeslotTime = Calendar.getInstance();
            {
                Calendar day = Calendar.getInstance();
                day.setTime(timeslot.getDate());
                timeslotTime.set(Calendar.DATE, day.get(Calendar.DATE));
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(timeslot.getEndTime());
                timeslotTime.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
            }
            if (timeslotTime.getTimeInMillis() < new Date().getTime()){
                timeslots.remove(timeslot);
            }
        }
        return timeslots;
    }
}