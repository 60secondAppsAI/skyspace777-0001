package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.LoyaltyProgram;





public interface LoyaltyProgramDAO extends GenericDAO<LoyaltyProgram, Integer> {
  
	List<LoyaltyProgram> findAll();
	






}


