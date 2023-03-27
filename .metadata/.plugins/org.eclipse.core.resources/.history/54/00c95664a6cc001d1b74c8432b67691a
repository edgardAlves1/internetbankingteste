package com.santander.ib.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.santander.ib.api.request.TransactionRequest;
import com.santander.ib.api.response.TransactionResponse;
import com.santander.ib.domain.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

	TransactionMapper INSTANCE =  Mappers.getMapper(TransactionMapper.class);
	
	Transaction toTransaction(TransactionRequest transactionRequest);
	
	List<Transaction> toTransaction(List<TransactionRequest> transactionRequestList);
	
	@Mapping(source = "customer.id", target = "customerId")
	TransactionResponse toTransactionResponse(Transaction transaction);
	
	List<TransactionResponse> toTransactionResponseList(List<Transaction> transactionList);
}
