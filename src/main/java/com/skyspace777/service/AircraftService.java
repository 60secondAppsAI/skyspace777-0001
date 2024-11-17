package com.skyspace777.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace777.domain.Aircraft;
import com.skyspace777.dto.AircraftDTO;
import com.skyspace777.dto.AircraftSearchDTO;
import com.skyspace777.dto.AircraftPageDTO;
import com.skyspace777.dto.AircraftConvertCriteriaDTO;
import com.skyspace777.service.GenericService;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface AircraftService extends GenericService<Aircraft, Integer> {

	List<Aircraft> findAll();

	ResultDTO addAircraft(AircraftDTO aircraftDTO, RequestDTO requestDTO);

	ResultDTO updateAircraft(AircraftDTO aircraftDTO, RequestDTO requestDTO);

    Page<Aircraft> getAllAircrafts(Pageable pageable);

    Page<Aircraft> getAllAircrafts(Specification<Aircraft> spec, Pageable pageable);

	ResponseEntity<AircraftPageDTO> getAircrafts(AircraftSearchDTO aircraftSearchDTO);
	
	List<AircraftDTO> convertAircraftsToAircraftDTOs(List<Aircraft> aircrafts, AircraftConvertCriteriaDTO convertCriteria);

	AircraftDTO getAircraftDTOById(Integer aircraftId);







}





