package com.sept.Thur10304.BookingSystem.repositories;

import com.sept.Thur10304.BookingSystem.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    
}