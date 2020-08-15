package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Service {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    // TODO
    // private Long adminId;

    private String serviceName;

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

}