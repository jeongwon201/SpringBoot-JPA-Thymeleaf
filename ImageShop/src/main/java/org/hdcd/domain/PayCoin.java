package org.hdcd.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "historyNo")
@ToString
@Entity
@Table(name = "pay_coin_history")
public class PayCoin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyNo;
	
	private Long userNo;
	private Long itemId;
	
	@Transient
	private String itemName;
	
	private int amount;
	
	@CreationTimestamp
	private LocalDateTime regDate;
	
	@UpdateTimestamp
	private LocalDateTime updDate;
}
