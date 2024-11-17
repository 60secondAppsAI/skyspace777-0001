package com.skyspace777.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="loyalty_programs")
@Getter @Setter @NoArgsConstructor
public class LoyaltyProgram {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="loyalty_program_id")
	private Integer loyaltyProgramId;
    
  	@Column(name="program_name")
	private String programName;
    
  	@Column(name="level_name")
	private String levelName;
    
  	@Column(name="points")
	private Integer points;
    
	




}