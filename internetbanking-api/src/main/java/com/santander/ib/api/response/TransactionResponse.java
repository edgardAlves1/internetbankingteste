package com.santander.ib.api.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
	
	private Long id;
	
	private Integer type ;
	
	private BigDecimal transactionValue;
	
	private Date transactionDate = new Date();
	
	private Long customerId;

}
