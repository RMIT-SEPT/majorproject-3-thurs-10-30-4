package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.services.Service_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class Service_Controller {
    
    @Autowired
    private Service_Service serviceService;

    @PostMapping("/save")
    public ResponseEntity<?> createNewService(@Valid @RequestBody Service_ service, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<String>("Invalid Account Object", HttpStatus.BAD_REQUEST);
        }
        Service_ service1 = serviceService.saveOrUpdateService(service);
        return new ResponseEntity<Service_>(service1, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllServices() {

        Iterable<Service_> services = serviceService.getAllServices();
        return new ResponseEntity<Iterable<Service_>>(services, HttpStatus.CREATED);
    }
}