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
import com.skyspace777.dao.LoyaltyProgramDAO;
import com.skyspace777.domain.LoyaltyProgram;
import com.skyspace777.dto.LoyaltyProgramDTO;
import com.skyspace777.dto.LoyaltyProgramSearchDTO;
import com.skyspace777.dto.LoyaltyProgramPageDTO;
import com.skyspace777.dto.LoyaltyProgramConvertCriteriaDTO;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import com.skyspace777.service.LoyaltyProgramService;
import com.skyspace777.util.ControllerUtils;





@Service
public class LoyaltyProgramServiceImpl extends GenericServiceImpl<LoyaltyProgram, Integer> implements LoyaltyProgramService {

    private final static Logger logger = LoggerFactory.getLogger(LoyaltyProgramServiceImpl.class);

	@Autowired
	LoyaltyProgramDAO loyaltyProgramDao;

	


	@Override
	public GenericDAO<LoyaltyProgram, Integer> getDAO() {
		return (GenericDAO<LoyaltyProgram, Integer>) loyaltyProgramDao;
	}
	
	public List<LoyaltyProgram> findAll () {
		List<LoyaltyProgram> loyaltyPrograms = loyaltyProgramDao.findAll();
		
		return loyaltyPrograms;	
		
	}

	public ResultDTO addLoyaltyProgram(LoyaltyProgramDTO loyaltyProgramDTO, RequestDTO requestDTO) {

		LoyaltyProgram loyaltyProgram = new LoyaltyProgram();

		loyaltyProgram.setLoyaltyProgramId(loyaltyProgramDTO.getLoyaltyProgramId());


		loyaltyProgram.setProgramName(loyaltyProgramDTO.getProgramName());


		loyaltyProgram.setLevelName(loyaltyProgramDTO.getLevelName());


		loyaltyProgram.setPoints(loyaltyProgramDTO.getPoints());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		loyaltyProgram = loyaltyProgramDao.save(loyaltyProgram);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<LoyaltyProgram> getAllLoyaltyPrograms(Pageable pageable) {
		return loyaltyProgramDao.findAll(pageable);
	}

	public Page<LoyaltyProgram> getAllLoyaltyPrograms(Specification<LoyaltyProgram> spec, Pageable pageable) {
		return loyaltyProgramDao.findAll(spec, pageable);
	}

	public ResponseEntity<LoyaltyProgramPageDTO> getLoyaltyPrograms(LoyaltyProgramSearchDTO loyaltyProgramSearchDTO) {
	
			Integer loyaltyProgramId = loyaltyProgramSearchDTO.getLoyaltyProgramId(); 
 			String programName = loyaltyProgramSearchDTO.getProgramName(); 
 			String levelName = loyaltyProgramSearchDTO.getLevelName(); 
 			Integer points = loyaltyProgramSearchDTO.getPoints(); 
 			String sortBy = loyaltyProgramSearchDTO.getSortBy();
			String sortOrder = loyaltyProgramSearchDTO.getSortOrder();
			String searchQuery = loyaltyProgramSearchDTO.getSearchQuery();
			Integer page = loyaltyProgramSearchDTO.getPage();
			Integer size = loyaltyProgramSearchDTO.getSize();

	        Specification<LoyaltyProgram> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, loyaltyProgramId, "loyaltyProgramId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, programName, "programName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, levelName, "levelName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, points, "points"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("programName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("levelName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<LoyaltyProgram> loyaltyPrograms = this.getAllLoyaltyPrograms(spec, pageable);
		
		//System.out.println(String.valueOf(loyaltyPrograms.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(loyaltyPrograms.getTotalPages()));
		
		List<LoyaltyProgram> loyaltyProgramsList = loyaltyPrograms.getContent();
		
		LoyaltyProgramConvertCriteriaDTO convertCriteria = new LoyaltyProgramConvertCriteriaDTO();
		List<LoyaltyProgramDTO> loyaltyProgramDTOs = this.convertLoyaltyProgramsToLoyaltyProgramDTOs(loyaltyProgramsList,convertCriteria);
		
		LoyaltyProgramPageDTO loyaltyProgramPageDTO = new LoyaltyProgramPageDTO();
		loyaltyProgramPageDTO.setLoyaltyPrograms(loyaltyProgramDTOs);
		loyaltyProgramPageDTO.setTotalElements(loyaltyPrograms.getTotalElements());
		return ResponseEntity.ok(loyaltyProgramPageDTO);
	}

	public List<LoyaltyProgramDTO> convertLoyaltyProgramsToLoyaltyProgramDTOs(List<LoyaltyProgram> loyaltyPrograms, LoyaltyProgramConvertCriteriaDTO convertCriteria) {
		
		List<LoyaltyProgramDTO> loyaltyProgramDTOs = new ArrayList<LoyaltyProgramDTO>();
		
		for (LoyaltyProgram loyaltyProgram : loyaltyPrograms) {
			loyaltyProgramDTOs.add(convertLoyaltyProgramToLoyaltyProgramDTO(loyaltyProgram,convertCriteria));
		}
		
		return loyaltyProgramDTOs;

	}
	
	public LoyaltyProgramDTO convertLoyaltyProgramToLoyaltyProgramDTO(LoyaltyProgram loyaltyProgram, LoyaltyProgramConvertCriteriaDTO convertCriteria) {
		
		LoyaltyProgramDTO loyaltyProgramDTO = new LoyaltyProgramDTO();
		
		loyaltyProgramDTO.setLoyaltyProgramId(loyaltyProgram.getLoyaltyProgramId());

	
		loyaltyProgramDTO.setProgramName(loyaltyProgram.getProgramName());

	
		loyaltyProgramDTO.setLevelName(loyaltyProgram.getLevelName());

	
		loyaltyProgramDTO.setPoints(loyaltyProgram.getPoints());

	

		
		return loyaltyProgramDTO;
	}

	public ResultDTO updateLoyaltyProgram(LoyaltyProgramDTO loyaltyProgramDTO, RequestDTO requestDTO) {
		
		LoyaltyProgram loyaltyProgram = loyaltyProgramDao.getById(loyaltyProgramDTO.getLoyaltyProgramId());

		loyaltyProgram.setLoyaltyProgramId(ControllerUtils.setValue(loyaltyProgram.getLoyaltyProgramId(), loyaltyProgramDTO.getLoyaltyProgramId()));

		loyaltyProgram.setProgramName(ControllerUtils.setValue(loyaltyProgram.getProgramName(), loyaltyProgramDTO.getProgramName()));

		loyaltyProgram.setLevelName(ControllerUtils.setValue(loyaltyProgram.getLevelName(), loyaltyProgramDTO.getLevelName()));

		loyaltyProgram.setPoints(ControllerUtils.setValue(loyaltyProgram.getPoints(), loyaltyProgramDTO.getPoints()));



        loyaltyProgram = loyaltyProgramDao.save(loyaltyProgram);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public LoyaltyProgramDTO getLoyaltyProgramDTOById(Integer loyaltyProgramId) {
	
		LoyaltyProgram loyaltyProgram = loyaltyProgramDao.getById(loyaltyProgramId);
			
		
		LoyaltyProgramConvertCriteriaDTO convertCriteria = new LoyaltyProgramConvertCriteriaDTO();
		return(this.convertLoyaltyProgramToLoyaltyProgramDTO(loyaltyProgram,convertCriteria));
	}







}
