package com.bank.main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.bank.loader.DataLoader;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.BankService;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		BankService bankService = new BankService();
		boolean running = true;
		DataLoader dataLoader = new DataLoader();
		dataLoader.loadAccountsFromFile("data/accounts.txt", bankService);
		while (running) {
			System.out.println("\n===== Bank Account Management System =====");
			System.out.println("1. Open Account");
			System.out.println("2. Deposit");
			System.out.println("3. Withdraw");
			System.out.println("4. Transfer Funds (by Account Number)");
			System.out.println("5. Send via UPI (by Mobile Number)");
			System.out.println("6. View Account Details");
			System.out.println("7. View Transaction History");
			System.out.println("8. View All Accounts");
			System.out.println("9. Close Account");
			System.out.println("10. Exit");
			System.out.print("Enter your choice: ");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine();
				continue;
			}

			switch (choice) {

			case 1:
			    System.out.print("Enter Holder Name: ");
			    String holderName = scanner.nextLine();

			    System.out.print("Enter Mobile Number: ");
			    String mobileNumber = scanner.nextLine();

			    System.out.print("Enter Initial Balance: ");
			    double initialBalance = scanner.nextDouble();
			    scanner.nextLine();

			    Account newAccount = bankService.openAccount(holderName, mobileNumber, initialBalance);
			    if (newAccount == null) {
			        System.out.println("Account creation failed. Check name, mobile number (10 digits, starting 6-9), and balance (must be non-negative).");
			    } else {
			        System.out.println("Account opened successfully! Your account number is: " + newAccount.getAccountNumber());
			    }
			    break;

			case 2:
				System.out.print("Enter Account Number: ");
				String depositAccountNumber = scanner.nextLine();

				System.out.print("Enter Amount to Deposit: ");
				double depositAmount = scanner.nextDouble();
				scanner.nextLine();

				boolean depositSuccess = bankService.deposit(depositAccountNumber, depositAmount);
				System.out.println(depositSuccess ? "Deposit successful!" : "Deposit failed. Account not found.");
				break;

			case 3:
				System.out.print("Enter Account Number: ");
				String withdrawAccountNumber = scanner.nextLine();

				System.out.print("Enter Amount to Withdraw: ");
				double withdrawAmount = scanner.nextDouble();
				scanner.nextLine();

				boolean withdrawSuccess = bankService.withdraw(withdrawAccountNumber, withdrawAmount);
				System.out.println(withdrawSuccess ? "Withdrawal successful!"
						: "Withdrawal failed. Insufficient balance or account not found.");
				break;

			case 4:
				System.out.print("Enter Your Account Number (From): ");
				String fromAccountNumber = scanner.nextLine();

				System.out.print("Enter Recipient Account Number (To): ");
				String toAccountNumber = scanner.nextLine();

				System.out.print("Enter Amount to Transfer: ");
				double transferAmount = scanner.nextDouble();
				scanner.nextLine();

				boolean transferSuccess = bankService.transfer(fromAccountNumber, toAccountNumber, transferAmount);
				System.out.println(transferSuccess ? "Transfer successful!"
						: "Transfer failed. Check account numbers and balance.");
				break;

			case 5:
				System.out.print("Enter Your Mobile Number (From): ");
				String fromMobile = scanner.nextLine();

				System.out.print("Enter Recipient Mobile Number (To): ");
				String toMobile = scanner.nextLine();

				System.out.print("Enter Amount to Send: ");
				double upiAmount = scanner.nextDouble();
				scanner.nextLine();

				boolean upiSuccess = bankService.transferByMobile(fromMobile, toMobile, upiAmount);
				System.out.println(upiSuccess ? "UPI transfer successful!"
						: "UPI transfer failed. Check mobile numbers and balance.");
				break;

			case 6:
				System.out.print("Enter Account Number: ");
				String viewAccountNumber = scanner.nextLine();

				Account account = bankService.getAccountDetails(viewAccountNumber);
				if (account == null) {
					System.out.println("Account not found.");
				} else {
					System.out.println(account);
				}
				break;

			case 7:
				System.out.print("Enter Account Number: ");
				String historyAccountNumber = scanner.nextLine();

				List<Transaction> history = bankService.getTransactionHistory(historyAccountNumber);
				if (history.isEmpty()) {
					System.out.println("No transactions found for this account.");
				} else {
					for (Transaction t : history) {
						System.out.println(t);
					}
				}
				break;

			case 8:
				List<Account> allAccounts = bankService.getAllAccounts();
				if (allAccounts.isEmpty()) {
					System.out.println("No accounts found.");
				} else {
					for (Account acc : allAccounts) {
						System.out.println(acc);
					}
				}
				break;

			case 9:
				System.out.print("Enter Account Number to Close: ");
				String closeAccountNumber = scanner.nextLine();

				boolean closeSuccess = bankService.closeAccount(closeAccountNumber);
				System.out.println(closeSuccess ? "Account closed successfully!"
						: "Close failed. Check account status or balance.");
				break;

			case 10:
				running = false;
				System.out.println("Exiting... Bye");
				break;
			default:
				System.out.println("Invalid choice, try again.");
			}
		}

		scanner.close();
	}
}