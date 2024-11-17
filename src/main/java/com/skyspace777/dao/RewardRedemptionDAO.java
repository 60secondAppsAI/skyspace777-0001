package com.skyspace777.dao;

import java.util.List;

import com.skyspace777.dao.GenericDAO;
import com.skyspace777.domain.RewardRedemption;





public interface RewardRedemptionDAO extends GenericDAO<RewardRedemption, Integer> {
  
	List<RewardRedemption> findAll();
	






}


