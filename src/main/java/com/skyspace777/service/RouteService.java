package com.skyspace777.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace777.domain.Route;
import com.skyspace777.dto.RouteDTO;
import com.skyspace777.dto.RouteSearchDTO;
import com.skyspace777.dto.RoutePageDTO;
import com.skyspace777.dto.RouteConvertCriteriaDTO;
import com.skyspace777.service.GenericService;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RouteService extends GenericService<Route, Integer> {

	List<Route> findAll();

	ResultDTO addRoute(RouteDTO routeDTO, RequestDTO requestDTO);

	ResultDTO updateRoute(RouteDTO routeDTO, RequestDTO requestDTO);

    Page<Route> getAllRoutes(Pageable pageable);

    Page<Route> getAllRoutes(Specification<Route> spec, Pageable pageable);

	ResponseEntity<RoutePageDTO> getRoutes(RouteSearchDTO routeSearchDTO);
	
	List<RouteDTO> convertRoutesToRouteDTOs(List<Route> routes, RouteConvertCriteriaDTO convertCriteria);

	RouteDTO getRouteDTOById(Integer routeId);







}





