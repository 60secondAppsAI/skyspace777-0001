package com.skyspace777.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RewardRedemptionPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<RewardRedemptionDTO> rewardRedemptions;
}





