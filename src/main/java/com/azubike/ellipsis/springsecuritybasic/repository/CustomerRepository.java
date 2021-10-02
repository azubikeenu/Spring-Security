package com.azubike.ellipsis.springsecuritybasic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.springsecuritybasic.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findByEmail(String email);
}
