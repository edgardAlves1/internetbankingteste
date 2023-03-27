package com.santander.ib.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santander.ib.api.mapper.CustomerMapper;
import com.santander.ib.api.mapper.TransactionMapper;
import com.santander.ib.api.request.CustomerRequest;
import com.santander.ib.api.request.TransactionRequest;
import com.santander.ib.api.response.CustomerResponse;
import com.santander.ib.api.response.TransactionFeeResponse;
import com.santander.ib.api.response.TransactionResponse;
import com.santander.ib.domain.entity.Customer;
import com.santander.ib.domain.entity.Transaction;
import com.santander.ib.domain.enums.TransactionType;
import com.santander.ib.exception.BusinessException;
import com.santander.ib.repository.CustomerRepository;
import com.santander.ib.repository.TransactionRepository;
import com.santander.ib.util.TransactionFeeUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	

	@Override
	public List<CustomerResponse> listAll() {
		return customerMapper.toListCustomerResponse(customerRepository.findAll());
	}
	
	@Override
	public Optional<Customer> getCustomerByIdOpt(Long id) {
		return customerRepository.findById(id);
	}
	
	@Override
	public CustomerResponse getCustomerById(Long id) {
		Optional<Customer> optCustomer = customerRepository.findById(id);
		
		if(optCustomer.isPresent()) {
			return customerMapper.toCustomerResponse(optCustomer.get());
		}else {
			throw new BusinessException("Cliente id não encontrado na base") ;
		}
		
	}

	@Override
    public CustomerResponse editCustomer(Long customerId, CustomerRequest customerRequest) {
        Optional<Customer> optCustomer = this.getCustomerByIdOpt(customerId);

        if (optCustomer.isEmpty()) {
            throw new BusinessException("Cliente não encontrado na base");
        }
        
        Customer customer = customerMapper.toCustomer(customerRequest);
        customer.setId(customerId);
        
        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }
	
	@Override
    public CustomerResponse deleteCustomer(Long customerId) {
        Optional<Customer> optCustomer = this.getCustomerByIdOpt(customerId);

        if (optCustomer.isEmpty()) {
            throw new BusinessException("Não foi possível excluir, pois cliente não existe na base");
        }
        
        customerRepository.delete(optCustomer.get());
        CustomerResponse customerResponse = customerMapper.toCustomerResponse(optCustomer.get());
        
        return customerResponse;
    }
	
	@Override
	public CustomerResponse save(CustomerRequest customerRequest) {
        boolean accountAlreadyRegistered = false;
        Customer customer = customerMapper.toCustomer(customerRequest);
        customer.getTransactions().forEach((t) -> t.setCustomer(customer));
        
        Optional<Customer> optCustomer = customerRepository.findByAccountNumber(customer.getAccountNumber());

        if (optCustomer.isPresent()) {
            if (!optCustomer.get().getId().equals(customer.getId())) {
            	accountAlreadyRegistered = true;
            }
        }

        if (accountAlreadyRegistered) {
            throw new BusinessException("Esta conta ja foi cadastrada");
        }

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }
	
	@Override
    public CustomerResponse performCustomerBankOperation(Long customerId, TransactionRequest transactionRequest) {
        Optional<Customer> optCustomer = this.getCustomerByIdOpt(customerId);

        if (optCustomer.isEmpty()) {
            throw new BusinessException("Não foi possível efetuar o saque, pois o cliente não encontrado na base");
        }

        return updateCustomerBalance(optCustomer.get(), transactionRequest);
    }
	
	private CustomerResponse updateCustomerBalance(Customer customer, TransactionRequest transactionRequest) {
		TransactionFeeResponse transactionFeeResponse = new TransactionFeeResponse();
		BigDecimal customerNewBalance;
		
		if(transactionRequest.getType().equals(TransactionType.WITHDRAW.getType())) {
			transactionFeeResponse = TransactionFeeUtil.calculateWithdrawFee(transactionRequest
					.getTransactionValue(), customer.getExclusivePlan());
			
			transactionFeeResponse.setTransactionType("Saque");
			transactionFeeResponse.setPreviousBalance(TransactionFeeUtil.formatToLocalCurrency(customer.getBalance()));
			
			customerNewBalance = customer.getBalance().subtract(transactionFeeResponse.getTransactionTotalResult()); 
			customer.setBalance(customerNewBalance);
			
			transactionFeeResponse.setNewBalance(TransactionFeeUtil.formatToLocalCurrency(customer.getBalance()));
		}else {
			transactionFeeResponse.setTransactionType("Depósito");
			transactionFeeResponse.setPreviousBalance(TransactionFeeUtil.formatToLocalCurrency(customer.getBalance()));
			
			customerNewBalance = customer.getBalance()
					.add(transactionRequest.getTransactionValue()); 
			customer.setBalance(customerNewBalance);
			
			transactionFeeResponse.setNewBalance(TransactionFeeUtil.formatToLocalCurrency(customer.getBalance()));
		}
		
		Transaction transaction = transactionMapper.toTransaction(transactionRequest);
		transaction.setCustomer(customer);
		customer.getTransactions().add(transaction);
		
		CustomerResponse customerResponse = customerMapper.toCustomerResponse(customerRepository.save(customer)); 
		customerResponse.setTransactionFeeResponse(transactionFeeResponse);
		
		return customerResponse;
    }
	
	@Override
	public List<TransactionResponse> listAllTransactionsByCustomerId(Long customerId) {
		return transactionMapper.toTransactionResponseList(transactionRepository.findAllByCustomerIdOrderByTransactionDateAsc(customerId));
	}
}