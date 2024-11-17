package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.CrewAssignment;





public interface CrewAssignmentDAO extends GenericDAO<CrewAssignment, Integer> {
  
	List<CrewAssignment> findAll();
	






}


