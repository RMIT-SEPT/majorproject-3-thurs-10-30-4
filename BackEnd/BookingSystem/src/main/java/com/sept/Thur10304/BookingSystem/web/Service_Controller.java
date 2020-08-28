package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.services.Service_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The controller for services
 * Manages the web api for dealing with services
 */
@RestController
@RequestMapping("/api/service")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Service_Controller {
    
    @Autowired
    private Service_Service serviceService;

    // Saves a service to the database
    @PostMapping("/save")
    public ResponseEntity<?> createNewService(@Valid @RequestBody Service_ service, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<String>("Invalid Service Object", HttpStatus.BAD_REQUEST);
        }
        Service_ service1 = serviceService.saveOrUpdateService(service);
        if (service1 != null){
            return new ResponseEntity<Service_>(service1, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Service name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    // Returns all registered services
    @GetMapping("/getall")
    public ResponseEntity<?> getAllServices() {

        Iterable<Service_> services = serviceService.getAllServices();
        return new ResponseEntity<Iterable<Service_>>(services, HttpStatus.FOUND);
    }


    // Inputs an ID of a service and returns the details to that service
    @GetMapping("/get")
    public ResponseEntity<?> getService(@RequestParam(name="id", required=true) String id) {
        // Gets the service from the database
        Service_ service = serviceService.getServiceById(id);
        // If service was found then return the service
        if (service != null){
            return new ResponseEntity<Service_>(service, HttpStatus.FOUND);
        // If no service was found then return an error message
        } else {
            return new ResponseEntity<String>("Service not found", HttpStatus.BAD_REQUEST);
        }
    }
}