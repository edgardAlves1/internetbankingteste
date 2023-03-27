package com.santander.ib.api.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionFeeResponse {
	
	private String transactionType;
	
	private String transactionValue;
	
	private String feeValue;
	
	private String transactionFeePercent;
	
	private String previousBalance;
	
	private String newBalance;
	
	private BigDecimal TransactionTotalResult;
}
