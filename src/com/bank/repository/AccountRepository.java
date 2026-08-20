package com.bank.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.model.Account;

public class AccountRepository {
	private Map<String, Account> accounts = new HashMap<>();

	public void addAccount(Account account) {
		accounts.put(account.getAccountNumber(), account);

	}

	public Account findByAccountNumber(String accountNumber) {
		return accounts.get(accountNumber);
	}

	public Account findByMobileNumber(String mobileNumber) {
		for (Account acc : accounts.values()) {
			if (acc.getMobileNumber().equals(mobileNumber)) {
				return acc;
			}
		}
		return null;
	}

	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accounts.values());
	}
}
