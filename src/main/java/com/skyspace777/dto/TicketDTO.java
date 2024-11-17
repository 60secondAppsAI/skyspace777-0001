package com.skyspace777.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class TicketDTO {

	private Integer ticketId;

	private String ticketNumber;

	private double price;

	private Date issueDate;






}
