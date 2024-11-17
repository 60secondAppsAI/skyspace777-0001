package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.Feedback;





public interface FeedbackDAO extends GenericDAO<Feedback, Integer> {
  
	List<Feedback> findAll();
	






}


