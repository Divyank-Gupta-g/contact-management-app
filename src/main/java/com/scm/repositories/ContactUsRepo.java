package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.ContactUs;

@Repository
public interface ContactUsRepo extends JpaRepository<ContactUs, String> {

}
