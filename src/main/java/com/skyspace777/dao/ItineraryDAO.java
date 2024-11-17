package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.Itinerary;





public interface ItineraryDAO extends GenericDAO<Itinerary, Integer> {
  
	List<Itinerary> findAll();
	






}


