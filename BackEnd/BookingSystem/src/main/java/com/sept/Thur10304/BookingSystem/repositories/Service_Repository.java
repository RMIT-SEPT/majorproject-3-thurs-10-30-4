package com.sept.Thur10304.BookingSystem.repositories;

import java.util.List;

import com.sept.Thur10304.BookingSystem.model.Service_;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Reposity used for interfacing with the database
 * Can use CRUD methods
 */
@Repository
public interface Service_Repository extends CrudRepository<Service_, Long> {

    Iterable<Service_> findByServiceNameIgnoreCase(String serviceName);
}