package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.model.Service_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

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

    public Iterable<Service_> getAllServices(){

        Iterable<Service_> services = serviceRepository.findAll();
        return services;
    }

    public Service_ getServiceById(String stringId){
        try {
            // Tries parsing the id into a long
            Long longId = Long.parseLong(stringId);
            // Tries to find a service with the inputted id
            Optional<Service_> service = serviceRepository.findById(longId);
            // If a service was found, then return that service
            if (service.isPresent()){
                return service.get();
            // If no service was found, then return null
            } else {
                return null;
            }
        // If there was an error parsing the long, return null
        } catch (NumberFormatException e) {
            return null;
        }
    }
}