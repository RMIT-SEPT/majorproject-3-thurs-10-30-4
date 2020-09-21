package com.sept.Thur10304.BookingSystem.repositories;

import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends CrudRepository<Timeslot, Long> {

    public Iterable<Timeslot> findByService(Service_ service);
}