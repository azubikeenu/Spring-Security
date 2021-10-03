package com.azubike.ellipsis.springsecuritybasic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.springsecuritybasic.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}