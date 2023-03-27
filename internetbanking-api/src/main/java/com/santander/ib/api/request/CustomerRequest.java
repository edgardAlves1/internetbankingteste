package com.santander.ib.api.request;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

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
public class CustomerRequest {
	
	
	private Long id;
	
	@NotBlank(message = "Nome do cliente é obrigatório")
	private String name;
	
	private Boolean exclusivePlan;
	
	private BigDecimal balance;
	
	@NotBlank(message = "Numero da conta é obrigatório")
	private String accountNumber;
	
	private Date birthDate = new Date();
	
	private List<TransactionRequest> transactions;
}