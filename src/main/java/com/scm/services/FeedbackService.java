package com.scm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Feedback;
import com.scm.repositories.FeedbackRepo;

@Service
public class FeedbackService {
	
	@Autowired
	private FeedbackRepo feedbackRepo;
	
	public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }
}

