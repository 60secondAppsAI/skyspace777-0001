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
import com.skyspace777.dao.CheckinDAO;
import com.skyspace777.domain.Checkin;
import com.skyspace777.dto.CheckinDTO;
import com.skyspace777.dto.CheckinSearchDTO;
import com.skyspace777.dto.CheckinPageDTO;
import com.skyspace777.dto.CheckinConvertCriteriaDTO;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import com.skyspace777.service.CheckinService;
import com.skyspace777.util.ControllerUtils;





@Service
public class CheckinServiceImpl extends GenericServiceImpl<Checkin, Integer> implements CheckinService {

    private final static Logger logger = LoggerFactory.getLogger(CheckinServiceImpl.class);

	@Autowired
	CheckinDAO checkinDao;

	


	@Override
	public GenericDAO<Checkin, Integer> getDAO() {
		return (GenericDAO<Checkin, Integer>) checkinDao;
	}
	
	public List<Checkin> findAll () {
		List<Checkin> checkins = checkinDao.findAll();
		
		return checkins;	
		
	}

	public ResultDTO addCheckin(CheckinDTO checkinDTO, RequestDTO requestDTO) {

		Checkin checkin = new Checkin();

		checkin.setCheckinId(checkinDTO.getCheckinId());


		checkin.setCheckinTime(checkinDTO.getCheckinTime());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		checkin = checkinDao.save(checkin);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Checkin> getAllCheckins(Pageable pageable) {
		return checkinDao.findAll(pageable);
	}

	public Page<Checkin> getAllCheckins(Specification<Checkin> spec, Pageable pageable) {
		return checkinDao.findAll(spec, pageable);
	}

	public ResponseEntity<CheckinPageDTO> getCheckins(CheckinSearchDTO checkinSearchDTO) {
	
			Integer checkinId = checkinSearchDTO.getCheckinId(); 
   			String sortBy = checkinSearchDTO.getSortBy();
			String sortOrder = checkinSearchDTO.getSortOrder();
			String searchQuery = checkinSearchDTO.getSearchQuery();
			Integer page = checkinSearchDTO.getPage();
			Integer size = checkinSearchDTO.getSize();

	        Specification<Checkin> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, checkinId, "checkinId"); 
			
 			

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

		Page<Checkin> checkins = this.getAllCheckins(spec, pageable);
		
		//System.out.println(String.valueOf(checkins.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(checkins.getTotalPages()));
		
		List<Checkin> checkinsList = checkins.getContent();
		
		CheckinConvertCriteriaDTO convertCriteria = new CheckinConvertCriteriaDTO();
		List<CheckinDTO> checkinDTOs = this.convertCheckinsToCheckinDTOs(checkinsList,convertCriteria);
		
		CheckinPageDTO checkinPageDTO = new CheckinPageDTO();
		checkinPageDTO.setCheckins(checkinDTOs);
		checkinPageDTO.setTotalElements(checkins.getTotalElements());
		return ResponseEntity.ok(checkinPageDTO);
	}

	public List<CheckinDTO> convertCheckinsToCheckinDTOs(List<Checkin> checkins, CheckinConvertCriteriaDTO convertCriteria) {
		
		List<CheckinDTO> checkinDTOs = new ArrayList<CheckinDTO>();
		
		for (Checkin checkin : checkins) {
			checkinDTOs.add(convertCheckinToCheckinDTO(checkin,convertCriteria));
		}
		
		return checkinDTOs;

	}
	
	public CheckinDTO convertCheckinToCheckinDTO(Checkin checkin, CheckinConvertCriteriaDTO convertCriteria) {
		
		CheckinDTO checkinDTO = new CheckinDTO();
		
		checkinDTO.setCheckinId(checkin.getCheckinId());

	
		checkinDTO.setCheckinTime(checkin.getCheckinTime());

	

		
		return checkinDTO;
	}

	public ResultDTO updateCheckin(CheckinDTO checkinDTO, RequestDTO requestDTO) {
		
		Checkin checkin = checkinDao.getById(checkinDTO.getCheckinId());

		checkin.setCheckinId(ControllerUtils.setValue(checkin.getCheckinId(), checkinDTO.getCheckinId()));

		checkin.setCheckinTime(ControllerUtils.setValue(checkin.getCheckinTime(), checkinDTO.getCheckinTime()));



        checkin = checkinDao.save(checkin);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CheckinDTO getCheckinDTOById(Integer checkinId) {
	
		Checkin checkin = checkinDao.getById(checkinId);
			
		
		CheckinConvertCriteriaDTO convertCriteria = new CheckinConvertCriteriaDTO();
		return(this.convertCheckinToCheckinDTO(checkin,convertCriteria));
	}







}
