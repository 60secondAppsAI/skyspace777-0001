package com.skyspace777.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.skyspace777.dao.GenericDAO;
import com.skyspace777.service.GenericService;
import com.skyspace777.service.impl.GenericServiceImpl;
import com.skyspace777.dao.RewardRedemptionDAO;
import com.skyspace777.domain.RewardRedemption;
import com.skyspace777.dto.RewardRedemptionDTO;
import com.skyspace777.dto.RewardRedemptionSearchDTO;
import com.skyspace777.dto.RewardRedemptionPageDTO;
import com.skyspace777.dto.RewardRedemptionConvertCriteriaDTO;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import com.skyspace777.service.RewardRedemptionService;
import com.skyspace777.util.ControllerUtils;





@Service
public class RewardRedemptionServiceImpl extends GenericServiceImpl<RewardRedemption, Integer> implements RewardRedemptionService {

    private final static Logger logger = LoggerFactory.getLogger(RewardRedemptionServiceImpl.class);

	@Autowired
	RewardRedemptionDAO rewardRedemptionDao;

	


	@Override
	public GenericDAO<RewardRedemption, Integer> getDAO() {
		return (GenericDAO<RewardRedemption, Integer>) rewardRedemptionDao;
	}
	
	public List<RewardRedemption> findAll () {
		List<RewardRedemption> rewardRedemptions = rewardRedemptionDao.findAll();
		
		return rewardRedemptions;	
		
	}

	public ResultDTO addRewardRedemption(RewardRedemptionDTO rewardRedemptionDTO, RequestDTO requestDTO) {

		RewardRedemption rewardRedemption = new RewardRedemption();

		rewardRedemption.setRewardRedemptionId(rewardRedemptionDTO.getRewardRedemptionId());


		rewardRedemption.setRedemptionDate(rewardRedemptionDTO.getRedemptionDate());


		rewardRedemption.setPointsRedeemed(rewardRedemptionDTO.getPointsRedeemed());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		rewardRedemption = rewardRedemptionDao.save(rewardRedemption);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<RewardRedemption> getAllRewardRedemptions(Pageable pageable) {
		return rewardRedemptionDao.findAll(pageable);
	}

	public Page<RewardRedemption> getAllRewardRedemptions(Specification<RewardRedemption> spec, Pageable pageable) {
		return rewardRedemptionDao.findAll(spec, pageable);
	}

	public ResponseEntity<RewardRedemptionPageDTO> getRewardRedemptions(RewardRedemptionSearchDTO rewardRedemptionSearchDTO) {
	
			Integer rewardRedemptionId = rewardRedemptionSearchDTO.getRewardRedemptionId(); 
   			Integer pointsRedeemed = rewardRedemptionSearchDTO.getPointsRedeemed(); 
 			String sortBy = rewardRedemptionSearchDTO.getSortBy();
			String sortOrder = rewardRedemptionSearchDTO.getSortOrder();
			String searchQuery = rewardRedemptionSearchDTO.getSearchQuery();
			Integer page = rewardRedemptionSearchDTO.getPage();
			Integer size = rewardRedemptionSearchDTO.getSize();

	        Specification<RewardRedemption> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, rewardRedemptionId, "rewardRedemptionId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, pointsRedeemed, "pointsRedeemed"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<RewardRedemption> rewardRedemptions = this.getAllRewardRedemptions(spec, pageable);
		
		//System.out.println(String.valueOf(rewardRedemptions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(rewardRedemptions.getTotalPages()));
		
		List<RewardRedemption> rewardRedemptionsList = rewardRedemptions.getContent();
		
		RewardRedemptionConvertCriteriaDTO convertCriteria = new RewardRedemptionConvertCriteriaDTO();
		List<RewardRedemptionDTO> rewardRedemptionDTOs = this.convertRewardRedemptionsToRewardRedemptionDTOs(rewardRedemptionsList,convertCriteria);
		
		RewardRedemptionPageDTO rewardRedemptionPageDTO = new RewardRedemptionPageDTO();
		rewardRedemptionPageDTO.setRewardRedemptions(rewardRedemptionDTOs);
		rewardRedemptionPageDTO.setTotalElements(rewardRedemptions.getTotalElements());
		return ResponseEntity.ok(rewardRedemptionPageDTO);
	}

	public List<RewardRedemptionDTO> convertRewardRedemptionsToRewardRedemptionDTOs(List<RewardRedemption> rewardRedemptions, RewardRedemptionConvertCriteriaDTO convertCriteria) {
		
		List<RewardRedemptionDTO> rewardRedemptionDTOs = new ArrayList<RewardRedemptionDTO>();
		
		for (RewardRedemption rewardRedemption : rewardRedemptions) {
			rewardRedemptionDTOs.add(convertRewardRedemptionToRewardRedemptionDTO(rewardRedemption,convertCriteria));
		}
		
		return rewardRedemptionDTOs;

	}
	
	public RewardRedemptionDTO convertRewardRedemptionToRewardRedemptionDTO(RewardRedemption rewardRedemption, RewardRedemptionConvertCriteriaDTO convertCriteria) {
		
		RewardRedemptionDTO rewardRedemptionDTO = new RewardRedemptionDTO();
		
		rewardRedemptionDTO.setRewardRedemptionId(rewardRedemption.getRewardRedemptionId());

	
		rewardRedemptionDTO.setRedemptionDate(rewardRedemption.getRedemptionDate());

	
		rewardRedemptionDTO.setPointsRedeemed(rewardRedemption.getPointsRedeemed());

	

		
		return rewardRedemptionDTO;
	}

	public ResultDTO updateRewardRedemption(RewardRedemptionDTO rewardRedemptionDTO, RequestDTO requestDTO) {
		
		RewardRedemption rewardRedemption = rewardRedemptionDao.getById(rewardRedemptionDTO.getRewardRedemptionId());

		rewardRedemption.setRewardRedemptionId(ControllerUtils.setValue(rewardRedemption.getRewardRedemptionId(), rewardRedemptionDTO.getRewardRedemptionId()));

		rewardRedemption.setRedemptionDate(ControllerUtils.setValue(rewardRedemption.getRedemptionDate(), rewardRedemptionDTO.getRedemptionDate()));

		rewardRedemption.setPointsRedeemed(ControllerUtils.setValue(rewardRedemption.getPointsRedeemed(), rewardRedemptionDTO.getPointsRedeemed()));



        rewardRedemption = rewardRedemptionDao.save(rewardRedemption);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RewardRedemptionDTO getRewardRedemptionDTOById(Integer rewardRedemptionId) {
	
		RewardRedemption rewardRedemption = rewardRedemptionDao.getById(rewardRedemptionId);
			
		
		RewardRedemptionConvertCriteriaDTO convertCriteria = new RewardRedemptionConvertCriteriaDTO();
		return(this.convertRewardRedemptionToRewardRedemptionDTO(rewardRedemption,convertCriteria));
	}







}
