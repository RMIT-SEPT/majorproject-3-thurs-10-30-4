package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.model.Service_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
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

        // TODO add way for same service to be updated, currently would return error for attempt to update with same service name

        //check that service is valid

        // Get all services that have the same name
        Iterator<Service_> servicesIterator = serviceRepository.findByServiceNameIgnoreCase(service.getServiceName()).iterator();
        // While there are services that have the same name
        while(servicesIterator.hasNext()){
            // Gets the next service and checks if it has the same name
            // (Would later add check to see if service is being updated here)
            Service_ currService = servicesIterator.next();
            if (service.getServiceName().toUpperCase().equals(currService.getServiceName().toUpperCase())){
                return null;
            }
        }

        // Save into repo
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