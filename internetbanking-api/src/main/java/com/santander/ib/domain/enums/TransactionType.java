package com.santander.ib.domain.enums;

public enum TransactionType {
	WITHDRAW(1),
	DEPOSIT(2);
	
	private Integer type;
	
	private TransactionType(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return this.type;
	}
}
