package com.sept.Thur10304.BookingSystem.repositories;

import com.sept.Thur10304.BookingSystem.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}