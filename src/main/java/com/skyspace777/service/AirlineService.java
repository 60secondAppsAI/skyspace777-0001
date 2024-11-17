package com.skyspace777.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace777.domain.Airline;
import com.skyspace777.dto.AirlineDTO;
import com.skyspace777.dto.AirlineSearchDTO;
import com.skyspace777.dto.AirlinePageDTO;
import com.skyspace777.dto.AirlineConvertCriteriaDTO;
import com.skyspace777.service.GenericService;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface AirlineService extends GenericService<Airline, Integer> {

	List<Airline> findAll();

	ResultDTO addAirline(AirlineDTO airlineDTO, RequestDTO requestDTO);

	ResultDTO updateAirline(AirlineDTO airlineDTO, RequestDTO requestDTO);

    Page<Airline> getAllAirlines(Pageable pageable);

    Page<Airline> getAllAirlines(Specification<Airline> spec, Pageable pageable);

	ResponseEntity<AirlinePageDTO> getAirlines(AirlineSearchDTO airlineSearchDTO);
	
	List<AirlineDTO> convertAirlinesToAirlineDTOs(List<Airline> airlines, AirlineConvertCriteriaDTO convertCriteria);

	AirlineDTO getAirlineDTOById(Integer airlineId);







}





