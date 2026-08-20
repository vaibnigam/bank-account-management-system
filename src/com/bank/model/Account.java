package com.bank.model;

public class Account {
	private String accountNumber;
	private String holderName;
	private String mobileNumber;
	private double balance;
	private AccountStatus status;

	public Account(String accountNumber, String holderName, String mobileNumber, double balance) {
		this.accountNumber = accountNumber;
		this.holderName = holderName;
		this.mobileNumber = mobileNumber;
		this.balance = balance;
		setStatus(AccountStatus.ACTIVE);
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", holderName=" + holderName + ", mobileNumber="
				+ mobileNumber + ", balance=" + balance + ", status=" + status + "]";
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

}