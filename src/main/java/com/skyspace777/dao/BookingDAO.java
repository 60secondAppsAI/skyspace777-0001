package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.Booking;





public interface BookingDAO extends GenericDAO<Booking, Integer> {
  
	List<Booking> findAll();
	






}


