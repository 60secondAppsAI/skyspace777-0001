package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.Airport;





public interface AirportDAO extends GenericDAO<Airport, Integer> {
  
	List<Airport> findAll();
	






}


