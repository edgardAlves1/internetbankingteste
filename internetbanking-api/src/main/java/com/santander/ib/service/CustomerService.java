package com.santander.ib.service;

import java.util.List;
import java.util.Optional;

import com.santander.ib.api.request.CustomerRequest;
import com.santander.ib.api.request.TransactionRequest;
import com.santander.ib.api.response.CustomerResponse;
import com.santander.ib.api.response.TransactionResponse;
import com.santander.ib.domain.entity.Customer;

public interface CustomerService {
	
	public List<CustomerResponse> listAll();
	
	public Optional<Customer> getCustomerByIdOpt(Long id);
	
	public CustomerResponse getCustomerById(Long id);
	
	public CustomerResponse save(CustomerRequest customerRequest);
	
	public CustomerResponse editCustomer(Long customerId, CustomerRequest customerRequest);
	
	public CustomerResponse deleteCustomer(Long customerId);
	
	public CustomerResponse performCustomerBankOperation(Long customerId, TransactionRequest customerTransactionRequest);
	
	public List<TransactionResponse> listAllTransactionsByCustomerId(Long customerId);
}
