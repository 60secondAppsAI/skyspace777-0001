package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.Route;





public interface RouteDAO extends GenericDAO<Route, Integer> {
  
	List<Route> findAll();
	






}


