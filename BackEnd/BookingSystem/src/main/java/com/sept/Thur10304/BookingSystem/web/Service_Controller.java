package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.security.JwtTokenProvider;
import com.sept.Thur10304.BookingSystem.model.Worker;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

    // Authentication for login
    @Autowired
    private JwtTokenProvider tokenProvider;

    // Saves a service to the database
    @PostMapping("/save/{adminId}")
    public ResponseEntity<?> createNewService(@Valid @RequestBody Service_ service, @PathVariable Long adminId,
      @RequestParam(name="token", required=true) String token, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<String>("Invalid Service Object", HttpStatus.BAD_REQUEST);
        }

        // Check that token is valid and id of login token is same as admin id
        if (!(tokenProvider.validateToken(token))){
            return new ResponseEntity <String>("Invalid token", HttpStatus.BAD_REQUEST);
        } else if (tokenProvider.getUserIdFromJWT(token) != adminId.longValue()){
            return new ResponseEntity <String>("Not logged in as same account as admin id", HttpStatus.BAD_REQUEST);
        }

        try {
            Service_ service1 = serviceService.saveOrUpdateService(service, adminId);
            return new ResponseEntity<Service_>(service1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<?> deleteService(@Valid @PathVariable Long serviceId,
      @RequestParam(name="token", required=true) String token) {

        // Checks if token is valid
        if (!(tokenProvider.validateToken(token))){
            return new ResponseEntity <String>("Invalid token", HttpStatus.BAD_REQUEST);

        } 
        try{
            if (!serviceService.verifyIfAdmin(serviceId, tokenProvider.getUserIdFromJWT(token))){
                return new ResponseEntity <String>("Not logged into admin of service", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // Run service to delete service and its timeslots from databse
        boolean serviceDeleted = serviceService.deleteService(serviceId);
        // If service found and deleted then return true, else false
        if (serviceDeleted){
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findbyadmin/{adminId}")
    public ResponseEntity<?> findServiceByAdmin(@Valid @PathVariable Long adminId) {
        try {
            Service_ service = serviceService.findServiceByAdmin(adminId);
            return new ResponseEntity<Service_>(service, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getavailableworkers/{serviceId}")
    public ResponseEntity<?> findWorkersForService(@Valid @PathVariable Long serviceId) {
        try {
            List<Worker> workers = serviceService.findWorkersForService(serviceId);
            return new ResponseEntity<List<Worker>>(workers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}