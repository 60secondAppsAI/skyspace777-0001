package com.skyspace777.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RewardRedemptionSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer rewardRedemptionId;
	
	private Date redemptionDate;
	
	private Integer pointsRedeemed;
	
}
