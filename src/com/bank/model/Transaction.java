package com.bank.model;

import java.time.LocalDateTime;

public class Transaction {
	private String transactionId;
	private String accountNumber;
	private TransactionType type;
	private double amount;
	private LocalDateTime timestamp;

	public Transaction(String transactionId, String accountNumber, TransactionType type, double amount) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.type = type;
		this.amount = amount;
		setTimestamp(LocalDateTime.now());
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountNumber=" + accountNumber + ", type=" + type
				+ ", amount=" + amount + ", timestamp=" + timestamp + "]";
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
