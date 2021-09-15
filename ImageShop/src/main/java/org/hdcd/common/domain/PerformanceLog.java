package org.hdcd.common.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "performance_log")
public class PerformanceLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logNo;
	
	@Column(length = 50, nullable = false)
	private String signatureName;
	
	@Column(length = 100, nullable = false)
	private String signatureTypeName;
	
	private Long durationTime;
	
	@CreationTimestamp
	private LocalDateTime regDate;
	
	@UpdateTimestamp
	private LocalDateTime updDate;
}
