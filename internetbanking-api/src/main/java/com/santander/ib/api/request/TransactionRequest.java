package com.santander.ib.api.request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
	
	private Integer type;
	
	private BigDecimal transactionValue;
	
	private Date transactionDate = new Date();

}
