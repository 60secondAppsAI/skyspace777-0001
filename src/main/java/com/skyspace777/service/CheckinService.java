package com.skyspace777.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace777.domain.Checkin;
import com.skyspace777.dto.CheckinDTO;
import com.skyspace777.dto.CheckinSearchDTO;
import com.skyspace777.dto.CheckinPageDTO;
import com.skyspace777.dto.CheckinConvertCriteriaDTO;
import com.skyspace777.service.GenericService;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CheckinService extends GenericService<Checkin, Integer> {

	List<Checkin> findAll();

	ResultDTO addCheckin(CheckinDTO checkinDTO, RequestDTO requestDTO);

	ResultDTO updateCheckin(CheckinDTO checkinDTO, RequestDTO requestDTO);

    Page<Checkin> getAllCheckins(Pageable pageable);

    Page<Checkin> getAllCheckins(Specification<Checkin> spec, Pageable pageable);

	ResponseEntity<CheckinPageDTO> getCheckins(CheckinSearchDTO checkinSearchDTO);
	
	List<CheckinDTO> convertCheckinsToCheckinDTOs(List<Checkin> checkins, CheckinConvertCriteriaDTO convertCriteria);

	CheckinDTO getCheckinDTOById(Integer checkinId);







}





