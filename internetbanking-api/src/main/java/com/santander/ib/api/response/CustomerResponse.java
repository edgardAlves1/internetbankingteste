package com.santander.ib.api.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
	
	private Long id;
	
	private String name;
	
	private Boolean exclusivePlan;
	
	private BigDecimal balance;
	
	private String accountNumber;
	
	private Date birthDate = new Date();
	
	private List<TransactionResponse> transactions;
	
	private TransactionFeeResponse transactionFeeResponse;
}
