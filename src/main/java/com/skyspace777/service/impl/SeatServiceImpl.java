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
import com.skyspace777.dao.SeatDAO;
import com.skyspace777.domain.Seat;
import com.skyspace777.dto.SeatDTO;
import com.skyspace777.dto.SeatSearchDTO;
import com.skyspace777.dto.SeatPageDTO;
import com.skyspace777.dto.SeatConvertCriteriaDTO;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import com.skyspace777.service.SeatService;
import com.skyspace777.util.ControllerUtils;





@Service
public class SeatServiceImpl extends GenericServiceImpl<Seat, Integer> implements SeatService {

    private final static Logger logger = LoggerFactory.getLogger(SeatServiceImpl.class);

	@Autowired
	SeatDAO seatDao;

	


	@Override
	public GenericDAO<Seat, Integer> getDAO() {
		return (GenericDAO<Seat, Integer>) seatDao;
	}
	
	public List<Seat> findAll () {
		List<Seat> seats = seatDao.findAll();
		
		return seats;	
		
	}

	public ResultDTO addSeat(SeatDTO seatDTO, RequestDTO requestDTO) {

		Seat seat = new Seat();

		seat.setSeatId(seatDTO.getSeatId());


		seat.setSeatNumber(seatDTO.getSeatNumber());


		seat.setClassCategory(seatDTO.getClassCategory());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		seat = seatDao.save(seat);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Seat> getAllSeats(Pageable pageable) {
		return seatDao.findAll(pageable);
	}

	public Page<Seat> getAllSeats(Specification<Seat> spec, Pageable pageable) {
		return seatDao.findAll(spec, pageable);
	}

	public ResponseEntity<SeatPageDTO> getSeats(SeatSearchDTO seatSearchDTO) {
	
			Integer seatId = seatSearchDTO.getSeatId(); 
 			String seatNumber = seatSearchDTO.getSeatNumber(); 
 			String classCategory = seatSearchDTO.getClassCategory(); 
 			String sortBy = seatSearchDTO.getSortBy();
			String sortOrder = seatSearchDTO.getSortOrder();
			String searchQuery = seatSearchDTO.getSearchQuery();
			Integer page = seatSearchDTO.getPage();
			Integer size = seatSearchDTO.getSize();

	        Specification<Seat> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, seatId, "seatId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, seatNumber, "seatNumber"); 
			
			spec = ControllerUtils.andIfNecessary(spec, classCategory, "classCategory"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("seatNumber")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("classCategory")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Seat> seats = this.getAllSeats(spec, pageable);
		
		//System.out.println(String.valueOf(seats.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(seats.getTotalPages()));
		
		List<Seat> seatsList = seats.getContent();
		
		SeatConvertCriteriaDTO convertCriteria = new SeatConvertCriteriaDTO();
		List<SeatDTO> seatDTOs = this.convertSeatsToSeatDTOs(seatsList,convertCriteria);
		
		SeatPageDTO seatPageDTO = new SeatPageDTO();
		seatPageDTO.setSeats(seatDTOs);
		seatPageDTO.setTotalElements(seats.getTotalElements());
		return ResponseEntity.ok(seatPageDTO);
	}

	public List<SeatDTO> convertSeatsToSeatDTOs(List<Seat> seats, SeatConvertCriteriaDTO convertCriteria) {
		
		List<SeatDTO> seatDTOs = new ArrayList<SeatDTO>();
		
		for (Seat seat : seats) {
			seatDTOs.add(convertSeatToSeatDTO(seat,convertCriteria));
		}
		
		return seatDTOs;

	}
	
	public SeatDTO convertSeatToSeatDTO(Seat seat, SeatConvertCriteriaDTO convertCriteria) {
		
		SeatDTO seatDTO = new SeatDTO();
		
		seatDTO.setSeatId(seat.getSeatId());

	
		seatDTO.setSeatNumber(seat.getSeatNumber());

	
		seatDTO.setClassCategory(seat.getClassCategory());

	

		
		return seatDTO;
	}

	public ResultDTO updateSeat(SeatDTO seatDTO, RequestDTO requestDTO) {
		
		Seat seat = seatDao.getById(seatDTO.getSeatId());

		seat.setSeatId(ControllerUtils.setValue(seat.getSeatId(), seatDTO.getSeatId()));

		seat.setSeatNumber(ControllerUtils.setValue(seat.getSeatNumber(), seatDTO.getSeatNumber()));

		seat.setClassCategory(ControllerUtils.setValue(seat.getClassCategory(), seatDTO.getClassCategory()));



        seat = seatDao.save(seat);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SeatDTO getSeatDTOById(Integer seatId) {
	
		Seat seat = seatDao.getById(seatId);
			
		
		SeatConvertCriteriaDTO convertCriteria = new SeatConvertCriteriaDTO();
		return(this.convertSeatToSeatDTO(seat,convertCriteria));
	}







}