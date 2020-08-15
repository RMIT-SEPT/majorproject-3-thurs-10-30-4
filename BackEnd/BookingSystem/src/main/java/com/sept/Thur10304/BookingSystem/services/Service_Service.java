package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.model.Service_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service for managing services.
 * Methods here are called by the web controller to interface with the repository
 */
@Service
public class Service_Service {
    @Autowired
    private Service_Repository serviceRepository;

    public Service_ saveOrUpdateService(Service_ service) {

        // TODO
        //check that service is valid
        return serviceRepository.save(service);
    }
}