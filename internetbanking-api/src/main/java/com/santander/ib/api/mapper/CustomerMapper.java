package com.santander.ib.api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.santander.ib.api.request.CustomerRequest;
import com.santander.ib.api.response.CustomerResponse;
import com.santander.ib.domain.entity.Customer;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface CustomerMapper {
	
	CustomerMapper INSTANCE =  Mappers.getMapper(CustomerMapper.class);
	
	public CustomerResponse toCustomerResponse(Customer customer);
	
	public List<CustomerResponse> toListCustomerResponse(List<Customer> customer);

	public Customer toCustomer(CustomerRequest customerRequest);

}