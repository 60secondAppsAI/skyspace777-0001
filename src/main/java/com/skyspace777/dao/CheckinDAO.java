package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.Checkin;





public interface CheckinDAO extends GenericDAO<Checkin, Integer> {
  
	List<Checkin> findAll();
	






}


