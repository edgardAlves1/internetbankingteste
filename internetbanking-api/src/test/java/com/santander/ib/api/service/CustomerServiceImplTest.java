package com.santander.ib.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.santander.ib.api.mapper.CustomerMapper;
import com.santander.ib.api.mapper.TransactionMapper;
import com.santander.ib.api.request.CustomerRequest;
import com.santander.ib.api.request.TransactionRequest;
import com.santander.ib.api.response.CustomerResponse;
import com.santander.ib.domain.entity.Customer;
import com.santander.ib.domain.entity.Transaction;
import com.santander.ib.repository.CustomerRepository;
import com.santander.ib.repository.TransactionRepository;
import com.santander.ib.service.CustomerServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CustomerServiceImplTest {
	
	@MockBean
	private CustomerMapper customerMapper;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	@MockBean
	private TransactionMapper transactionMapper;
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@Test
	public void listAllCustomersOkTest() {
		customerService.listAll();
	}
	
	@Test
	public void getCustomerByIdOptTest() {
		Long customerId = 1L;
		customerService.getCustomerByIdOpt(customerId);
	}
	
	@Test
	public void deleteCustomerByIdTest() {
		Long customerId = 1L;
		Optional<Customer> optCustomer = Optional.of(new Customer());
		Mockito.when(customerRepository.findById(customerId)).thenReturn(optCustomer);
		
		customerService.deleteCustomer(customerId);
		
		Mockito.verify(customerRepository, Mockito.times(1)).delete(ArgumentMatchers.any(Customer.class));
	}
	
	@Test
	public void getCustomerByIdTest() {
		Long customerId = 1L;
		Optional<Customer> optCustomer = Optional.of(new Customer());
		Mockito.when(customerRepository.findById(customerId)).thenReturn(optCustomer);
		
		customerService.getCustomerById(customerId);
	}
	
	private Customer getCustomer() {
		Customer customer = new Customer();
        customer.setAccountNumber("123456");
        customer.setBalance(new BigDecimal(500.00));
        customer.setBirthDate(new Date());
        customer.setExclusivePlan(false);
        customer.setId(1L);
        customer.setName("Cliente Mock");
        
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setId(1L);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionValue(new BigDecimal(550.00));
        transaction.setType(1);
        
        transactions.add(transaction);
        customer.setTransactions(transactions);
        
        return customer;
	}
	
	@Test
	public void editCustomerByIdTest() {
		Long customerId = 1L;
		Optional<Customer> optCustomer = Optional.of(new Customer());
		
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setAccountNumber("12346578");
		customerRequest.setBalance(new BigDecimal(500.00));
		customerRequest.setBirthDate(new Date());
		customerRequest.setExclusivePlan(false);
		customerRequest.setId(1L);
		customerRequest.setName("Customer");
   
        List<TransactionRequest> transactionsRequest = new ArrayList<>();
        TransactionRequest transactionRequestList = new TransactionRequest();
        transactionRequestList.setTransactionDate(new Date());
        transactionRequestList.setTransactionValue(new BigDecimal(550.00));
        transactionRequestList.setType(1);
        transactionsRequest.add(transactionRequestList);
		customerRequest.setTransactions(transactionsRequest);
		
		Customer customer = getCustomer();
		
		Mockito.when(customerRepository.findById(customerId)).thenReturn(optCustomer);
		Mockito.when(customerRepository.save(new Customer())).thenReturn(customer);
		Mockito.when(customerMapper.toCustomer(customerRequest)).thenReturn(customer);
		
		customerService.save(customerRequest);
	}

}
