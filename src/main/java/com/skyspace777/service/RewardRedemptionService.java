package com.skyspace777.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace777.domain.RewardRedemption;
import com.skyspace777.dto.RewardRedemptionDTO;
import com.skyspace777.dto.RewardRedemptionSearchDTO;
import com.skyspace777.dto.RewardRedemptionPageDTO;
import com.skyspace777.dto.RewardRedemptionConvertCriteriaDTO;
import com.skyspace777.service.GenericService;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RewardRedemptionService extends GenericService<RewardRedemption, Integer> {

	List<RewardRedemption> findAll();

	ResultDTO addRewardRedemption(RewardRedemptionDTO rewardRedemptionDTO, RequestDTO requestDTO);

	ResultDTO updateRewardRedemption(RewardRedemptionDTO rewardRedemptionDTO, RequestDTO requestDTO);

    Page<RewardRedemption> getAllRewardRedemptions(Pageable pageable);

    Page<RewardRedemption> getAllRewardRedemptions(Specification<RewardRedemption> spec, Pageable pageable);

	ResponseEntity<RewardRedemptionPageDTO> getRewardRedemptions(RewardRedemptionSearchDTO rewardRedemptionSearchDTO);
	
	List<RewardRedemptionDTO> convertRewardRedemptionsToRewardRedemptionDTOs(List<RewardRedemption> rewardRedemptions, RewardRedemptionConvertCriteriaDTO convertCriteria);

	RewardRedemptionDTO getRewardRedemptionDTOById(Integer rewardRedemptionId);







}





