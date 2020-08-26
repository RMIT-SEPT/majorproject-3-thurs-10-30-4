package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.repositories.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sept.Thur10304.BookingSystem.model.Service_;
import java.util.Date;

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
}