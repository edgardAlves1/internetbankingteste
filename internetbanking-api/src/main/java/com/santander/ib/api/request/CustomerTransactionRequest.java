package com.santander.ib.api.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import com.santander.ib.domain.enums.TransactionType;
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
public class CustomerTransactionRequest {
	
	private Long id;
	
	@NotBlank(message = "valor da transação é obrigatório")
	private BigDecimal transactionValue;
	
	@NotBlank(message = "Tipo da transação é obrigatório para saque 'WITHDRAW' para deposito 'DEPOSIT'")
	private TransactionType transactionType;
	
	private Boolean exclusivePlan;
}
