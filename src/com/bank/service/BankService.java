package com.bank.service;

import java.util.List;

import com.bank.model.Account;
import com.bank.model.AccountStatus;
import com.bank.model.Transaction;
import com.bank.model.TransactionType;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;

public class BankService {
	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;

	public BankService() {
		this.accountRepository = new AccountRepository();
		this.transactionRepository = new TransactionRepository();
	}

	private int nextAccountNumber = 1101;

	public Account openAccount(String holderName, String mobileNumber, double initialBalance) {
		if (holderName == null || holderName.trim().isEmpty()) {
			return null;
		}
		if (mobileNumber == null || !mobileNumber.matches("[6-9][0-9]{9}")) {
			return null;
		}
		if (initialBalance < 0) {
			return null;
		}
		String accountNumber = "AC" + nextAccountNumber;
		nextAccountNumber++;
		Account newAccount = new Account(accountNumber, holderName, mobileNumber, initialBalance);
		accountRepository.addAccount(newAccount);
		return newAccount;
	}

	// for data load
	public void loadAccountDirectly(String accountNumber, String holderName, String mobileNumber, double balance) {
		Account newAccount = new Account(accountNumber, holderName, mobileNumber, balance);
		accountRepository.addAccount(newAccount);
	}

	private boolean isActive(Account account) {
		return account != null && account.getStatus() == AccountStatus.ACTIVE;
	}

	public boolean deposit(String accountNumber, double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (!isActive(account)) {
			return false;
		}

		account.setBalance(account.getBalance() + amount);

		String transactionId = String.valueOf(System.currentTimeMillis());

		Transaction transaction = new Transaction(transactionId, accountNumber, TransactionType.DEPOSIT, amount);
		transactionRepository.addTransaction(transaction);
		return true;
	}

	public boolean withdraw(String accountNumber, double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);

		if (!isActive(account) || account.getBalance() < amount) {
			return false;
		}
		account.setBalance(account.getBalance() - amount);
		String transactionId = String.valueOf(System.currentTimeMillis());

		Transaction transaction = new Transaction(transactionId, accountNumber, TransactionType.WITHDRAW, amount);
		transactionRepository.addTransaction(transaction);
		return true;
	}

	private boolean transferBetweenAccounts(Account from, Account to, double amount) {
		if (!isActive(to) || !isActive(from) || from.getBalance() < amount) {
			return false;
		}
		from.setBalance(from.getBalance() - amount);
		to.setBalance(to.getBalance() + amount);
		String transactionId = String.valueOf(System.currentTimeMillis());

		Transaction transactionFrom = new Transaction(transactionId, from.getAccountNumber(),
				TransactionType.TRANSFER_OUT, amount);
		Transaction transactionTo = new Transaction(transactionId, to.getAccountNumber(), TransactionType.TRANSFER_IN,
				amount);
		transactionRepository.addTransaction(transactionFrom);
		transactionRepository.addTransaction(transactionTo);
		return true;
	}

	public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
		Account from = accountRepository.findByAccountNumber(fromAccountNumber);
		Account to = accountRepository.findByAccountNumber(toAccountNumber);
		return transferBetweenAccounts(from, to, amount);

	}

	public boolean transferByMobile(String fromMobile, String toMobile, double amount) {
		Account from = accountRepository.findByMobileNumber(fromMobile);
		Account to = accountRepository.findByMobileNumber(toMobile);
		return transferBetweenAccounts(from, to, amount);
	}

	public boolean closeAccount(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null || account.getStatus() == AccountStatus.CLOSED) {
			return false;
		}
		if (account.getBalance() != 0) {
			return false;
		}
		account.setStatus(AccountStatus.CLOSED);
		return true;
	}

	public Account getAccountDetails(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

	public List<Transaction> getTransactionHistory(String accountNumber) {
		return transactionRepository.getTransactionsByAccount(accountNumber);
	}

	public List<Account> getAllAccounts() {
		return accountRepository.getAllAccounts();
	}

}
