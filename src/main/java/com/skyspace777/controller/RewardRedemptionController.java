package com.skyspace777.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.skyspace777.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.skyspace777.domain.RewardRedemption;
import com.skyspace777.dto.RewardRedemptionDTO;
import com.skyspace777.dto.RewardRedemptionSearchDTO;
import com.skyspace777.dto.RewardRedemptionPageDTO;
import com.skyspace777.service.RewardRedemptionService;
import com.skyspace777.dto.common.RequestDTO;
import com.skyspace777.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/rewardRedemption")
@RestController
public class RewardRedemptionController {

	private final static Logger logger = LoggerFactory.getLogger(RewardRedemptionController.class);

	@Autowired
	RewardRedemptionService rewardRedemptionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<RewardRedemption> getAll() {

		List<RewardRedemption> rewardRedemptions = rewardRedemptionService.findAll();
		
		return rewardRedemptions;	
	}

	@GetMapping(value = "/{rewardRedemptionId}")
	@ResponseBody
	public RewardRedemptionDTO getRewardRedemption(@PathVariable Integer rewardRedemptionId) {
		
		return (rewardRedemptionService.getRewardRedemptionDTOById(rewardRedemptionId));
	}

 	@RequestMapping(value = "/addRewardRedemption", method = RequestMethod.POST)
	public ResponseEntity<?> addRewardRedemption(@RequestBody RewardRedemptionDTO rewardRedemptionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = rewardRedemptionService.addRewardRedemption(rewardRedemptionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/rewardRedemptions")
	public ResponseEntity<RewardRedemptionPageDTO> getRewardRedemptions(RewardRedemptionSearchDTO rewardRedemptionSearchDTO) {
 
		return rewardRedemptionService.getRewardRedemptions(rewardRedemptionSearchDTO);
	}	

	@RequestMapping(value = "/updateRewardRedemption", method = RequestMethod.POST)
	public ResponseEntity<?> updateRewardRedemption(@RequestBody RewardRedemptionDTO rewardRedemptionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = rewardRedemptionService.updateRewardRedemption(rewardRedemptionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
