# Bank Account Management System

A console-based Bank Account Management System built in core Java, using the Collections framework and enums to model account state and transaction records. This project extends the layered architecture used in earlier systems with two new patterns: an immutable transaction log and a shared, DRY-compliant fund-transfer engine that supports both account-number-based and UPI-style mobile-number-based transfers.

## Overview

This application allows a bank administrator to manage customer accounts — opening accounts, processing deposits and withdrawals, transferring funds between accounts (by account number or by mobile number, UPI-style), viewing account and transaction details, and closing accounts. Every balance-affecting operation is recorded as an immutable `Transaction`, giving each account a full, queryable history.

## Architecture

```
Main (Presentation) → BankService (Business Logic) → AccountRepository / TransactionRepository (Data Layer) → Account / Transaction (Model)
```

- **Model** — `Account`: holds account number, holder name, mobile number, balance, and status. `Transaction`: an immutable record of a single balance-affecting event (type, amount, timestamp). `AccountStatus` and `TransactionType` are enums, chosen over `String` constants to get compile-time safety and eliminate invalid or mistyped state values.
- **Repository** — `AccountRepository`: backed by a single `Map<String, Account>` keyed by account number, with mobile-number lookups implemented as a linear scan for simplicity. `TransactionRepository`: backed by a `Map<String, List<Transaction>>`, keyed by account number, where each value is the append-only list of that account's transaction history.
- **Service** — `BankService`: holds references to both repositories and contains all business rules — balance sufficiency checks, account-closure eligibility, and the transfer engine.
- **Loader** — `DataLoader`: reads and parses the startup accounts file, kept separate from `BankService` to preserve single-responsibility.
- **Main** — the console entry point; displays the menu and delegates all operations to `BankService`.

## Features

- Open an account
- Deposit funds
- Withdraw funds (blocked if it would overdraw the account)
- Transfer funds between two accounts by account number
- Send funds via UPI — transfer by mobile number instead of account number
- View full details of a single account
- View the complete transaction history of an account
- View all accounts
- Close an account (only permitted when the balance is exactly zero)
- Bulk-load accounts from a `.txt` file at startup

## Project Structure

```
src/
  com/bank/model/       → Account.java, Transaction.java, AccountStatus.java, TransactionType.java
  com/bank/repository/  → AccountRepository.java, TransactionRepository.java
  com/bank/service/     → BankService.java
  com/bank/loader/      → DataLoader.java
  com/bank/main/        → Main.java
data/
  accounts.txt           → sample account data loaded at startup
```

## Data File Format

`data/accounts.txt` uses comma-separated values, one account per line:

```
accountNumber,holderName,mobileNumber,initialBalance
```

Example:
```
AC1001,Chirag Nair,9126855092,25000
AC1002,Shreya Kapoor,9339670711,1500
```

## Tech Stack

- Java 17
- `java.util` Collections (`HashMap`, `ArrayList`, `List`, `Map`)
- `java.time.LocalDateTime` for transaction timestamps
- Console-based I/O via `Scanner`

## Running the Project

1. Import the project into Eclipse (or any Java IDE).
2. Ensure `data/accounts.txt` exists in the project root.
3. Run `Main.java`.

## Design Notes

### Enums over strings
`AccountStatus` (`ACTIVE`, `CLOSED`) and `TransactionType` (`DEPOSIT`, `WITHDRAW`, `TRANSFER_IN`, `TRANSFER_OUT`) are modeled as enums rather than string constants. This removes an entire class of bugs — typos, inconsistent casing, and invalid values — that a `String`-based status field would allow to compile silently.

### Immutable transaction log
Transactions are never updated or deleted once created. Every deposit, withdrawal, and transfer leg appends a new `Transaction` to the account's history, which mirrors how real financial ledgers preserve an audit trail rather than mutating past records.

### Closing an account never deletes it
Closing an account does not remove it from the repository — it transitions `AccountStatus` from `ACTIVE` to `CLOSED`. This keeps the account's transaction history valid and queryable indefinitely, and avoids orphaning `Transaction` records that reference an account number that no longer exists. Closure is only permitted when the balance is exactly zero, preventing funds from being silently discarded.

### A single, shared transfer engine
Both `transfer()` (by account number) and `transferByMobile()` (UPI-style, by mobile number) resolve their source and destination accounts differently, but both delegate the actual balance movement and transaction recording to a single private method, `transferBetweenAccounts()`. This keeps the transfer logic — balance validation, the debit/credit pair, and the `TRANSFER_OUT`/`TRANSFER_IN` transaction pair — defined in exactly one place, so a future change to transfer rules (e.g. a transfer fee) only needs to be made once.

### Dual balance-check before mutation
Both `withdraw()` and `transferBetweenAccounts()` validate sufficient balance before mutating any state, and resolve the account only once per call rather than looking it up twice — avoiding redundant repository lookups and ensuring the balance check and the mutation always operate on the same object reference.