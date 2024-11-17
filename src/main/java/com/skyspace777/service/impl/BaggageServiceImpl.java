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
import com.skyspace777.dao.BaggageDAO;
import com.skyspace777.domain.Baggage;
import com.skyspace777.dto.BaggageDTO;
import com.skyspace777.dto.BaggageSearchDTO;
import com.skyspace777.dto.BaggagePageDTO;
import com.skyspace777.dto.BaggageConvertCriteriaDTO;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;
import com.skyspace777.service.BaggageService;
import com.skyspace777.util.ControllerUtils;





@Service
public class BaggageServiceImpl extends GenericServiceImpl<Baggage, Integer> implements BaggageService {

    private final static Logger logger = LoggerFactory.getLogger(BaggageServiceImpl.class);

	@Autowired
	BaggageDAO baggageDao;

	


	@Override
	public GenericDAO<Baggage, Integer> getDAO() {
		return (GenericDAO<Baggage, Integer>) baggageDao;
	}
	
	public List<Baggage> findAll () {
		List<Baggage> baggages = baggageDao.findAll();
		
		return baggages;	
		
	}

	public ResultDTO addBaggage(BaggageDTO baggageDTO, RequestDTO requestDTO) {

		Baggage baggage = new Baggage();

		baggage.setBaggageId(baggageDTO.getBaggageId());


		baggage.setWeight(baggageDTO.getWeight());


		baggage.setType(baggageDTO.getType());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		baggage = baggageDao.save(baggage);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Baggage> getAllBaggages(Pageable pageable) {
		return baggageDao.findAll(pageable);
	}

	public Page<Baggage> getAllBaggages(Specification<Baggage> spec, Pageable pageable) {
		return baggageDao.findAll(spec, pageable);
	}

	public ResponseEntity<BaggagePageDTO> getBaggages(BaggageSearchDTO baggageSearchDTO) {
	
			Integer baggageId = baggageSearchDTO.getBaggageId(); 
  			String type = baggageSearchDTO.getType(); 
 			String sortBy = baggageSearchDTO.getSortBy();
			String sortOrder = baggageSearchDTO.getSortOrder();
			String searchQuery = baggageSearchDTO.getSearchQuery();
			Integer page = baggageSearchDTO.getPage();
			Integer size = baggageSearchDTO.getSize();

	        Specification<Baggage> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, baggageId, "baggageId"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, type, "type"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("type")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Baggage> baggages = this.getAllBaggages(spec, pageable);
		
		//System.out.println(String.valueOf(baggages.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(baggages.getTotalPages()));
		
		List<Baggage> baggagesList = baggages.getContent();
		
		BaggageConvertCriteriaDTO convertCriteria = new BaggageConvertCriteriaDTO();
		List<BaggageDTO> baggageDTOs = this.convertBaggagesToBaggageDTOs(baggagesList,convertCriteria);
		
		BaggagePageDTO baggagePageDTO = new BaggagePageDTO();
		baggagePageDTO.setBaggages(baggageDTOs);
		baggagePageDTO.setTotalElements(baggages.getTotalElements());
		return ResponseEntity.ok(baggagePageDTO);
	}

	public List<BaggageDTO> convertBaggagesToBaggageDTOs(List<Baggage> baggages, BaggageConvertCriteriaDTO convertCriteria) {
		
		List<BaggageDTO> baggageDTOs = new ArrayList<BaggageDTO>();
		
		for (Baggage baggage : baggages) {
			baggageDTOs.add(convertBaggageToBaggageDTO(baggage,convertCriteria));
		}
		
		return baggageDTOs;

	}
	
	public BaggageDTO convertBaggageToBaggageDTO(Baggage baggage, BaggageConvertCriteriaDTO convertCriteria) {
		
		BaggageDTO baggageDTO = new BaggageDTO();
		
		baggageDTO.setBaggageId(baggage.getBaggageId());

	
		baggageDTO.setWeight(baggage.getWeight());

	
		baggageDTO.setType(baggage.getType());

	

		
		return baggageDTO;
	}

	public ResultDTO updateBaggage(BaggageDTO baggageDTO, RequestDTO requestDTO) {
		
		Baggage baggage = baggageDao.getById(baggageDTO.getBaggageId());

		baggage.setBaggageId(ControllerUtils.setValue(baggage.getBaggageId(), baggageDTO.getBaggageId()));

		baggage.setWeight(ControllerUtils.setValue(baggage.getWeight(), baggageDTO.getWeight()));

		baggage.setType(ControllerUtils.setValue(baggage.getType(), baggageDTO.getType()));



        baggage = baggageDao.save(baggage);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BaggageDTO getBaggageDTOById(Integer baggageId) {
	
		Baggage baggage = baggageDao.getById(baggageId);
			
		
		BaggageConvertCriteriaDTO convertCriteria = new BaggageConvertCriteriaDTO();
		return(this.convertBaggageToBaggageDTO(baggage,convertCriteria));
	}







}
