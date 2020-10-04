package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;

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

    @Autowired
    private AccountService accountService;

    @Autowired
    private AdminRepository adminRepository;

    public Service_ saveOrUpdateService(Service_ service, Long adminId) throws Exception {

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
                throw new Exception("Service name already exists");
            }
        }

        // Check that admin exists
        Optional<Admin> findAdmin = adminRepository.findById(adminId);
        if (!findAdmin.isPresent()){
            throw new Exception("Admin not found");
        } else if (findAdmin.get().getService() != null){
            throw new Exception("Admin already has service");
        }
        
        service.setAdmin(findAdmin.get());

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

    public boolean deleteService(Long serviceId){
        // Finds the service by its id
        Optional<Service_> service = serviceRepository.findById(serviceId);

        // If service is found, then delete it and return true, else return false
        if (service.isPresent()){
            // Note: also deletes all timeslots associated with service
            serviceRepository.delete(service.get());
            return true;
        } else {
            return false;
        }
    }

    public Service_ findServiceByAdmin(Long adminId) throws Exception {
        // Finds admin
        Admin admin = accountService.findAdmin(adminId);

        // Returns service (null if no service)
        return admin.getService();
    }

    public List<Worker> findWorkersForService(Long serviceId) throws Exception {
        // Finds service
        Optional<Service_> findService = serviceRepository.findById(serviceId);

        // If service not found then throw exceptioon
        if (!findService.isPresent()){
            throw new Exception("Service not found");
        }

        Service_ service = findService.get();

        // Gets admin that manages service
        Admin admin = service.getAdmin();

        // Returns workers that the admin manages
        return admin.getWorkers();
    }
}