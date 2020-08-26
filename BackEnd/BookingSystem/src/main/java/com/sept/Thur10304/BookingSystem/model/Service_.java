package com.sept.Thur10304.BookingSystem.model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entity for storing information about services offered on the site
 * Different the services contained in the services package
 */
@Entity
public class Service_ {
    //TODO validation
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    // TODO
    // private Long adminId;

    @Size(min = 2,max = 20, message = "Service name must be between 2 and 20 characters in length")
    @NotBlank(message = "Service requires a name")
    private String serviceName;

    @Size(min = 10, max = 100, message = "Service description must be between 10 and 100 characters in length")
    @NotBlank(message = "Service requires a description")
    private String serviceDescription;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Timeslot> timeslots;

    public Long getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    
    public String getServiceDescription() {
        return this.serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}