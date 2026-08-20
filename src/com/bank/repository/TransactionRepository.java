package com.bank.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.model.Transaction;

public class TransactionRepository {
	private Map<String, List<Transaction>> transactionsByAccount = new HashMap<>();

	public void addTransaction(Transaction transaction) {
		String accountNumber = transaction.getAccountNumber();

		if (!transactionsByAccount.containsKey(accountNumber)) {
			transactionsByAccount.put(accountNumber, new ArrayList<Transaction>());
		}

		transactionsByAccount.get(accountNumber).add(transaction);
	}

	public List<Transaction> getTransactionsByAccount(String accountNumber) {
		List<Transaction> transactions = transactionsByAccount.get(accountNumber);
		if (transactions == null) {
			return new ArrayList<Transaction>();
		}
		return transactions;
	}
}
