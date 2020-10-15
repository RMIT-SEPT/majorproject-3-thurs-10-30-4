package com.sept.Thur10304.BookingSystem.repositories;

import com.sept.Thur10304.BookingSystem.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Override
    Iterable<Account> findAllById(Iterable<Long> iterable);

    Account findByEmail(String email);
    Account getById(Long id);
}