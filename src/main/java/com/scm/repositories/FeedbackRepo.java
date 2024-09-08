package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

}
