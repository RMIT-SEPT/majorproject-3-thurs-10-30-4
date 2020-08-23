package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.repositories.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeslotService {

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private Service_Service serviceService;

    public Timeslot saveOrUpdateTimeslot(Timeslot timeslot) {
        // TODO do basic checks
        // check for if service exists
        if (serviceService.getServiceById(Long.toString(timeslot.getServiceId())) != null){
            return timeslotRepository.save(timeslot);
        } else {
            return null;
        }
    }
}