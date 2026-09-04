package com.bank.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.loader.DataLoader;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.BankService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;


public class WebServer {

    private static BankService bankService;


    // ============================================================
    // MAIN
    // ============================================================

    public static void main(String[] args) throws Exception {

        // Create BankService
        bankService = new BankService();


        // Load existing accounts from accounts.txt
        DataLoader dataLoader = new DataLoader();

        dataLoader.loadAccountsFromFile(
                "Data/accounts.txt",
                bankService
        );


        // Create HTTP Server
        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );


        // ========================================================
        // URL ROUTES
        // ========================================================

        server.createContext("/", WebServer::homePage);

        server.createContext(
                "/open",
                WebServer::openAccountPage
        );

        server.createContext(
                "/deposit",
                WebServer::depositPage
        );

        server.createContext(
                "/withdraw",
                WebServer::withdrawPage
        );

        server.createContext(
                "/transfer",
                WebServer::transferPage
        );

        server.createContext(
                "/upi",
                WebServer::upiPage
        );

        server.createContext(
                "/account",
                WebServer::accountPage
        );

        server.createContext(
                "/transactions",
                WebServer::transactionPage
        );

        server.createContext(
                "/accounts",
                WebServer::allAccountsPage
        );

        server.createContext(
                "/close",
                WebServer::closeAccountPage
        );


        // Start server
        server.start();


        System.out.println();
        System.out.println("==========================================");
        System.out.println(" Bank Account Management Web Application");
        System.out.println("==========================================");
        System.out.println("Server started successfully!");
        System.out.println();
        System.out.println("Open:");
        System.out.println("http://localhost:8080");
        System.out.println("==========================================");
    }


    // ============================================================
    // HOME PAGE
    // ============================================================

    private static void homePage(HttpExchange exchange)
            throws IOException {

        String html = """

                <!DOCTYPE html>

                <html>

                <head>

                    <meta charset="UTF-8">

                    <title>
                        Bank Account Management System
                    </title>

                    <style>

                        * {
                            box-sizing: border-box;
                        }

                        body {
                            margin: 0;
                            font-family: Arial, sans-serif;
                            background: #f4f6f8;
                        }

                        .header {
                            background: #1e3a8a;
                            color: white;
                            padding: 35px;
                            text-align: center;
                        }

                        .header h1 {
                            margin: 0;
                            font-size: 34px;
                        }

                        .header p {
                            margin-top: 10px;
                            font-size: 18px;
                        }

                        .container {
                            width: 90%;
                            max-width: 1100px;
                            margin: 40px auto;
                        }

                        .menu {
                            display: grid;
                            grid-template-columns:
                                repeat(3, 1fr);

                            gap: 25px;
                        }

                        .card {
                            background: white;
                            padding: 30px;
                            border-radius: 12px;
                            text-align: center;

                            box-shadow:
                                0 3px 12px
                                rgba(0, 0, 0, 0.10);

                            transition: 0.2s;
                        }

                        .card:hover {
                            transform: translateY(-3px);
                        }

                        .card h2 {
                            color: #1e3a8a;
                            margin-top: 0;
                        }

                        .card p {
                            color: #555;
                            min-height: 40px;
                        }

                        .button {
                            display: inline-block;
                            padding: 11px 20px;

                            background: #2563eb;
                            color: white;

                            text-decoration: none;

                            border-radius: 6px;

                            margin-top: 10px;
                        }

                        .button:hover {
                            background: #1d4ed8;
                        }

                        .footer {
                            text-align: center;
                            color: #777;
                            padding: 30px;
                        }

                        @media(max-width: 850px) {

                            .menu {
                                grid-template-columns:
                                    repeat(2, 1fr);
                            }
                        }

                        @media(max-width: 550px) {

                            .menu {
                                grid-template-columns: 1fr;
                            }
                        }

                    </style>

                </head>


                <body>


                    <div class="header">

                        <h1>
                            🏦 Bank Account Management System
                        </h1>

                        <p>
                            Core Java Banking Application
                        </p>

                    </div>


                    <div class="container">

                        <div class="menu">


                            <div class="card">

                                <h2>
                                    Open Account
                                </h2>

                                <p>
                                    Create a new bank account
                                </p>

                                <a
                                    href="/open"
                                    class="button">

                                    Open Account

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    Deposit
                                </h2>

                                <p>
                                    Deposit money into an account
                                </p>

                                <a
                                    href="/deposit"
                                    class="button">

                                    Deposit

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    Withdraw
                                </h2>

                                <p>
                                    Withdraw money from an account
                                </p>

                                <a
                                    href="/withdraw"
                                    class="button">

                                    Withdraw

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    Transfer Funds
                                </h2>

                                <p>
                                    Transfer using account number
                                </p>

                                <a
                                    href="/transfer"
                                    class="button">

                                    Transfer

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    UPI Transfer
                                </h2>

                                <p>
                                    Transfer using mobile number
                                </p>

                                <a
                                    href="/upi"
                                    class="button">

                                    Send via UPI

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    Account Details
                                </h2>

                                <p>
                                    View account information
                                </p>

                                <a
                                    href="/account"
                                    class="button">

                                    View Account

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    Transaction History
                                </h2>

                                <p>
                                    View account transactions
                                </p>

                                <a
                                    href="/transactions"
                                    class="button">

                                    View Transactions

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    All Accounts
                                </h2>

                                <p>
                                    View all bank accounts
                                </p>

                                <a
                                    href="/accounts"
                                    class="button">

                                    View Accounts

                                </a>

                            </div>


                            <div class="card">

                                <h2>
                                    Close Account
                                </h2>

                                <p>
                                    Close an existing account
                                </p>

                                <a
                                    href="/close"
                                    class="button">

                                    Close Account

                                </a>

                            </div>


                        </div>

                    </div>


                    <div class="footer">

                        Bank Account Management System

                    </div>


                </body>

                </html>

                """;


        sendResponse(exchange, html);
    }


    // ============================================================
    // OPEN ACCOUNT
    // ============================================================

    private static void openAccountPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "Open New Account",

                    """

                    <form method="POST" action="/open">

                        <label>
                            Account Holder Name
                        </label>

                        <input
                            type="text"
                            name="holderName"
                            placeholder="Enter holder name"
                            required>


                        <label>
                            Mobile Number
                        </label>

                        <input
                            type="text"
                            name="mobileNumber"
                            placeholder="10 digit mobile number"
                            maxlength="10"
                            required>


                        <label>
                            Initial Balance
                        </label>

                        <input
                            type="number"
                            name="initialBalance"
                            placeholder="Enter initial balance"
                            min="0"
                            step="0.01"
                            required>


                        <button type="submit">
                            Create Account
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            String holderName =
                    data.get("holderName");

            String mobileNumber =
                    data.get("mobileNumber");


            Account account = null;


            try {

                double initialBalance =
                        Double.parseDouble(
                                data.get("initialBalance")
                        );


                account =
                        bankService.openAccount(
                                holderName,
                                mobileNumber,
                                initialBalance
                        );

            }
            catch (Exception e) {

                account = null;
            }


            if (account != null) {

                String html = resultPage(
                        "Account Created Successfully!",
                        "Your account number is: "
                                + account.getAccountNumber(),
                        true
                );

                sendResponse(exchange, html);

            }
            else {

                String html = resultPage(
                        "Account Creation Failed",
                        """
                        Please check the entered details.
                        <br><br>
                        Mobile number must be 10 digits
                        and start with 6-9.
                        <br>
                        Balance cannot be negative.
                        """,
                        false
                );

                sendResponse(exchange, html);
            }

            return;
        }
    }


    // ============================================================
    // DEPOSIT
    // ============================================================

    private static void depositPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "Deposit Money",

                    """

                    <form method="POST" action="/deposit">

                        <label>
                            Account Number
                        </label>

                        <input
                            type="text"
                            name="accountNumber"
                            placeholder="Example: AC1001"
                            required>


                        <label>
                            Amount
                        </label>

                        <input
                            type="number"
                            name="amount"
                            placeholder="Enter amount"
                            min="0.01"
                            step="0.01"
                            required>


                        <button type="submit">
                            Deposit
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            String accountNumber =
                    data.get("accountNumber");


            boolean success = false;


            try {

                double amount =
                        Double.parseDouble(
                                data.get("amount")
                        );


                success =
                        bankService.deposit(
                                accountNumber,
                                amount
                        );

            }
            catch (Exception e) {

                success = false;
            }


            if (success) {

                Account account =
                        bankService.getAccountDetails(
                                accountNumber
                        );


                String html = resultPage(
                        "Deposit Successful!",
                        """
                        Amount deposited: ₹%.2f
                        <br><br>
                        Updated Balance: ₹%.2f
                        """
                        .formatted(
                                Double.parseDouble(
                                        data.get("amount")
                                ),
                                account.getBalance()
                        ),
                        true
                );

                sendResponse(exchange, html);

            }
            else {

                String html = resultPage(
                        "Deposit Failed",
                        "Account not found, inactive, or invalid amount.",
                        false
                );

                sendResponse(exchange, html);
            }

            return;
        }
    }


    // ============================================================
    // WITHDRAW
    // ============================================================

    private static void withdrawPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "Withdraw Money",

                    """

                    <form method="POST" action="/withdraw">

                        <label>
                            Account Number
                        </label>

                        <input
                            type="text"
                            name="accountNumber"
                            placeholder="Example: AC1001"
                            required>


                        <label>
                            Amount
                        </label>

                        <input
                            type="number"
                            name="amount"
                            placeholder="Enter amount"
                            min="0.01"
                            step="0.01"
                            required>


                        <button type="submit">
                            Withdraw
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            String accountNumber =
                    data.get("accountNumber");


            boolean success = false;


            try {

                double amount =
                        Double.parseDouble(
                                data.get("amount")
                        );


                success =
                        bankService.withdraw(
                                accountNumber,
                                amount
                        );

            }
            catch (Exception e) {

                success = false;
            }


            if (success) {

                Account account =
                        bankService.getAccountDetails(
                                accountNumber
                        );


                String html = resultPage(
                        "Withdrawal Successful!",
                        """
                        Amount withdrawn: ₹%.2f
                        <br><br>
                        Remaining Balance: ₹%.2f
                        """
                        .formatted(
                                Double.parseDouble(
                                        data.get("amount")
                                ),
                                account.getBalance()
                        ),
                        true
                );

                sendResponse(exchange, html);

            }
            else {

                String html = resultPage(
                        "Withdrawal Failed",
                        "Account not found, inactive, or insufficient balance.",
                        false
                );

                sendResponse(exchange, html);
            }

            return;
        }
    }


    // ============================================================
    // TRANSFER
    // ============================================================

    private static void transferPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "Transfer Funds",

                    """

                    <form method="POST" action="/transfer">

                        <label>
                            Your Account Number
                        </label>

                        <input
                            type="text"
                            name="fromAccountNumber"
                            placeholder="Example: AC1001"
                            required>


                        <label>
                            Recipient Account Number
                        </label>

                        <input
                            type="text"
                            name="toAccountNumber"
                            placeholder="Example: AC1002"
                            required>


                        <label>
                            Amount
                        </label>

                        <input
                            type="number"
                            name="amount"
                            placeholder="Enter amount"
                            min="0.01"
                            step="0.01"
                            required>


                        <button type="submit">
                            Transfer Money
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            boolean success = false;


            try {

                double amount =
                        Double.parseDouble(
                                data.get("amount")
                        );


                success =
                        bankService.transfer(
                                data.get("fromAccountNumber"),
                                data.get("toAccountNumber"),
                                amount
                        );

            }
            catch (Exception e) {

                success = false;
            }


            String html;


            if (success) {

                html = resultPage(
                        "Transfer Successful!",
                        "Money has been transferred successfully.",
                        true
                );

            }
            else {

                html = resultPage(
                        "Transfer Failed",
                        "Check account numbers, account status, and balance.",
                        false
                );
            }


            sendResponse(exchange, html);

            return;
        }
    }


    // ============================================================
    // UPI TRANSFER
    // ============================================================

    private static void upiPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "UPI Transfer",

                    """

                    <form method="POST" action="/upi">

                        <label>
                            Your Mobile Number
                        </label>

                        <input
                            type="text"
                            name="fromMobile"
                            placeholder="10 digit mobile number"
                            maxlength="10"
                            required>


                        <label>
                            Recipient Mobile Number
                        </label>

                        <input
                            type="text"
                            name="toMobile"
                            placeholder="10 digit mobile number"
                            maxlength="10"
                            required>


                        <label>
                            Amount
                        </label>

                        <input
                            type="number"
                            name="amount"
                            placeholder="Enter amount"
                            min="0.01"
                            step="0.01"
                            required>


                        <button type="submit">
                            Send Money
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            boolean success = false;


            try {

                double amount =
                        Double.parseDouble(
                                data.get("amount")
                        );


                success =
                        bankService.transferByMobile(
                                data.get("fromMobile"),
                                data.get("toMobile"),
                                amount
                        );

            }
            catch (Exception e) {

                success = false;
            }


            String html;


            if (success) {

                html = resultPage(
                        "UPI Transfer Successful!",
                        "Money has been sent successfully.",
                        true
                );

            }
            else {

                html = resultPage(
                        "UPI Transfer Failed",
                        "Check mobile numbers and available balance.",
                        false
                );
            }


            sendResponse(exchange, html);

            return;
        }
    }


    // ============================================================
    // ACCOUNT DETAILS
    // ============================================================

    private static void accountPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "View Account Details",

                    """

                    <form method="POST" action="/account">

                        <label>
                            Account Number
                        </label>

                        <input
                            type="text"
                            name="accountNumber"
                            placeholder="Example: AC1001"
                            required>


                        <button type="submit">
                            View Account
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            String accountNumber =
                    data.get("accountNumber");


            Account account =
                    bankService.getAccountDetails(
                            accountNumber
                    );


            String html;


            if (account == null) {

                html = resultPage(
                        "Account Not Found",
                        "No account exists with this account number.",
                        false
                );

            }
            else {

                html = """

                        <!DOCTYPE html>

                        <html>

                        <head>

                            <meta charset="UTF-8">

                            <title>
                                Account Details
                            </title>

                            %s

                        </head>

                        <body>

                            <div class="container">

                                <div class="card">

                                    <h1>
                                        Account Details
                                    </h1>

                                    <table>

                                        <tr>
                                            <th>Account Number</th>
                                            <td>%s</td>
                                        </tr>

                                        <tr>
                                            <th>Holder Name</th>
                                            <td>%s</td>
                                        </tr>

                                        <tr>
                                            <th>Mobile Number</th>
                                            <td>%s</td>
                                        </tr>

                                        <tr>
                                            <th>Balance</th>
                                            <td>₹%.2f</td>
                                        </tr>

                                        <tr>
                                            <th>Status</th>
                                            <td>%s</td>
                                        </tr>

                                    </table>

                                    <a
                                        href="/"
                                        class="button">

                                        Back to Home

                                    </a>

                                </div>

                            </div>

                        </body>

                        </html>

                        """.formatted(
                                CSS,
                                account.getAccountNumber(),
                                account.getHolderName(),
                                account.getMobileNumber(),
                                account.getBalance(),
                                account.getStatus()
                        );
            }


            sendResponse(exchange, html);

            return;
        }
    }


    // ============================================================
    // TRANSACTION HISTORY
    // ============================================================

    private static void transactionPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "Transaction History",

                    """

                    <form method="POST" action="/transactions">

                        <label>
                            Account Number
                        </label>

                        <input
                            type="text"
                            name="accountNumber"
                            placeholder="Example: AC1001"
                            required>


                        <button type="submit">
                            View Transactions
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            String accountNumber =
                    data.get("accountNumber");


            List<Transaction> transactions =
                    bankService.getTransactionHistory(
                            accountNumber
                    );


            StringBuilder rows =
                    new StringBuilder();


            for (Transaction transaction :
                    transactions) {


                rows.append(
                        """

                        <tr>

                            <td>%s</td>

                            <td>%s</td>

                            <td>%s</td>

                            <td>₹%.2f</td>

                            <td>%s</td>

                        </tr>

                        """.formatted(
                                transaction.getTransactionId(),
                                transaction.getAccountNumber(),
                                transaction.getType(),
                                transaction.getAmount(),
                                transaction.getTimestamp()
                        )
                );
            }


            String html;


            if (transactions.isEmpty()) {

                html = resultPage(
                        "No Transactions",
                        "No transactions found for this account.",
                        false
                );

            }
            else {

                html = """

                        <!DOCTYPE html>

                        <html>

                        <head>

                            <meta charset="UTF-8">

                            <title>
                                Transaction History
                            </title>

                            %s

                        </head>


                        <body>

                            <div class="container">

                                <div class="card">

                                    <h1>
                                        Transaction History
                                    </h1>

                                    <p>
                                        Account:
                                        <strong>%s</strong>
                                    </p>


                                    <table>

                                        <tr>

                                            <th>
                                                Transaction ID
                                            </th>

                                            <th>
                                                Account
                                            </th>

                                            <th>
                                                Type
                                            </th>

                                            <th>
                                                Amount
                                            </th>

                                            <th>
                                                Timestamp
                                            </th>

                                        </tr>

                                        %s

                                    </table>


                                    <br>


                                    <a
                                        href="/"
                                        class="button">

                                        Back to Home

                                    </a>

                                </div>

                            </div>

                        </body>

                        </html>

                        """.formatted(
                                CSS,
                                accountNumber,
                                rows.toString()
                        );
            }


            sendResponse(exchange, html);

            return;
        }
    }


    // ============================================================
    // ALL ACCOUNTS
    // ============================================================

    private static void allAccountsPage(
            HttpExchange exchange) throws IOException {


        List<Account> accounts =
                bankService.getAllAccounts();


        StringBuilder rows =
                new StringBuilder();


        for (Account account : accounts) {

            rows.append(
                    """

                    <tr>

                        <td>%s</td>

                        <td>%s</td>

                        <td>%s</td>

                        <td>₹%.2f</td>

                        <td>%s</td>

                    </tr>

                    """.formatted(
                            account.getAccountNumber(),
                            account.getHolderName(),
                            account.getMobileNumber(),
                            account.getBalance(),
                            account.getStatus()
                    )
            );
        }


        String html = """

                <!DOCTYPE html>

                <html>

                <head>

                    <meta charset="UTF-8">

                    <title>
                        All Accounts
                    </title>

                    %s

                </head>


                <body>

                    <div class="container">

                        <div class="card">

                            <h1>
                                All Accounts
                            </h1>

                            <p>
                                Total Accounts:
                                <strong>%d</strong>
                            </p>


                            <table>

                                <tr>

                                    <th>
                                        Account Number
                                    </th>

                                    <th>
                                        Holder Name
                                    </th>

                                    <th>
                                        Mobile
                                    </th>

                                    <th>
                                        Balance
                                    </th>

                                    <th>
                                        Status
                                    </th>

                                </tr>

                                %s

                            </table>


                            <br>


                            <a
                                href="/"
                                class="button">

                                Back to Home

                            </a>

                        </div>

                    </div>

                </body>

                </html>

                """.formatted(
                        CSS,
                        accounts.size(),
                        rows.toString()
                );


        sendResponse(exchange, html);
    }


    // ============================================================
    // CLOSE ACCOUNT
    // ============================================================

    private static void closeAccountPage(
            HttpExchange exchange) throws IOException {


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {


            String html = formPage(
                    "Close Account",

                    """

                    <form method="POST" action="/close">

                        <label>
                            Account Number
                        </label>

                        <input
                            type="text"
                            name="accountNumber"
                            placeholder="Example: AC1001"
                            required>


                        <button type="submit">
                            Close Account
                        </button>

                    </form>

                    """
            );


            sendResponse(exchange, html);

            return;
        }


        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {


            Map<String, String> data =
                    getFormData(exchange);


            String accountNumber =
                    data.get("accountNumber");


            boolean success =
                    bankService.closeAccount(
                            accountNumber
                    );


            String html;


            if (success) {

                html = resultPage(
                        "Account Closed Successfully!",
                        "Account " + accountNumber
                                + " has been closed.",
                        true
                );

            }
            else {

                html = resultPage(
                        "Unable to Close Account",
                        """
                        Account may not exist,
                        may already be closed,
                        or balance must be zero.
                        """,
                        false
                );
            }


            sendResponse(exchange, html);

            return;
        }
    }


    // ============================================================
    // COMMON FORM PAGE
    // ============================================================

    private static String formPage(
            String title,
            String formContent) {


        return """

                <!DOCTYPE html>

                <html>

                <head>

                    <meta charset="UTF-8">

                    <title>%s</title>

                    %s

                </head>


                <body>

                    <div class="container">

                        <div class="card">

                            <h1>
                                %s
                            </h1>

                            %s

                            <a
                                href="/"
                                class="back">

                                ← Back to Home

                            </a>

                        </div>

                    </div>

                </body>

                </html>

                """.formatted(
                        title,
                        CSS,
                        title,
                        formContent
                );
    }


    // ============================================================
    // RESULT PAGE
    // ============================================================

    private static String resultPage(
            String title,
            String message,
            boolean success) {


        String icon =
                success ? "✅" : "❌";


        String cssClass =
                success ? "success" : "error";


        return """

                <!DOCTYPE html>

                <html>

                <head>

                    <meta charset="UTF-8">

                    <title>%s</title>

                    %s

                </head>


                <body>

                    <div class="container">

                        <div class="card">

                            <div class="result-icon">
                                %s
                            </div>

                            <h1 class="%s">
                                %s
                            </h1>

                            <p class="message">
                                %s
                            </p>

                            <a
                                href="/"
                                class="button">

                                Back to Home

                            </a>

                        </div>

                    </div>

                </body>

                </html>

                """.formatted(
                        title,
                        CSS,
                        icon,
                        cssClass,
                        title,
                        message
                );
    }


    // ============================================================
    // PARSE FORM DATA
    // ============================================================

    private static Map<String, String> getFormData(
            HttpExchange exchange)
            throws IOException {


        String requestBody =
                new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                );


        return parseFormData(requestBody);
    }


    private static Map<String, String> parseFormData(
            String body) {


        Map<String, String> data =
                new HashMap<>();


        if (body == null || body.isEmpty()) {
            return data;
        }


        String[] pairs =
                body.split("&");


        for (String pair : pairs) {


            String[] keyValue =
                    pair.split("=", 2);


            if (keyValue.length == 2) {


                String key =
                        URLDecoder.decode(
                                keyValue[0],
                                StandardCharsets.UTF_8
                        );


                String value =
                        URLDecoder.decode(
                                keyValue[1],
                                StandardCharsets.UTF_8
                        );


                data.put(key, value);
            }
        }


        return data;
    }


    // ============================================================
    // SEND RESPONSE
    // ============================================================

    private static void sendResponse(
            HttpExchange exchange,
            String html)
            throws IOException {


        byte[] response =
                html.getBytes(
                        StandardCharsets.UTF_8
                );


        exchange.getResponseHeaders().set(
                "Content-Type",
                "text/html; charset=UTF-8"
        );


        exchange.sendResponseHeaders(
                200,
                response.length
        );


        try (OutputStream outputStream =
                     exchange.getResponseBody()) {

            outputStream.write(response);
        }
    }


    // ============================================================
    // COMMON CSS
    // ============================================================

    private static final String CSS = """

            <style>

                * {
                    box-sizing: border-box;
                }


                body {

                    margin: 0;

                    font-family:
                        Arial, sans-serif;

                    background:
                        #f4f6f8;
                }


                .container {

                    width: 90%;

                    max-width: 900px;

                    margin: 60px auto;
                }


                .card {

                    background: white;

                    padding: 35px;

                    border-radius: 12px;

                    box-shadow:
                        0 3px 12px
                        rgba(0,0,0,0.10);
                }


                h1 {

                    text-align: center;

                    color:
                        #1e3a8a;

                    margin-top: 0;

                    margin-bottom: 30px;
                }


                label {

                    display: block;

                    margin-top: 18px;

                    margin-bottom: 7px;

                    font-weight: bold;

                    color: #333;
                }


                input {

                    width: 100%;

                    padding: 12px;

                    border:
                        1px solid #ccc;

                    border-radius: 6px;

                    font-size: 15px;
                }


                input:focus {

                    outline: none;

                    border-color:
                        #2563eb;
                }


                button {

                    width: 100%;

                    margin-top: 25px;

                    padding: 13px;

                    background:
                        #2563eb;

                    color: white;

                    border: none;

                    border-radius: 6px;

                    font-size: 16px;

                    cursor: pointer;
                }


                button:hover {

                    background:
                        #1d4ed8;
                }


                .back {

                    display: block;

                    text-align: center;

                    margin-top: 20px;

                    color:
                        #2563eb;

                    text-decoration: none;
                }


                .button {

                    display: inline-block;

                    padding:
                        11px 20px;

                    background:
                        #2563eb;

                    color: white;

                    text-decoration: none;

                    border-radius: 6px;
                }


                .button:hover {

                    background:
                        #1d4ed8;
                }


                .success {

                    color:
                        #15803d;
                }


                .error {

                    color:
                        #dc2626;
                }


                .result-icon {

                    text-align: center;

                    font-size: 60px;

                    margin-bottom: 15px;
                }


                .message {

                    text-align: center;

                    font-size: 18px;

                    line-height: 1.7;

                    margin-bottom: 30px;
                }


                table {

                    width: 100%;

                    border-collapse:
                        collapse;

                    margin-top: 20px;
                }


                th {

                    background:
                        #1e3a8a;

                    color: white;

                    padding: 12px;

                    text-align: left;
                }


                td {

                    padding: 12px;

                    border-bottom:
                        1px solid #ddd;
                }


                tr:hover {

                    background:
                        #f8fafc;
                }


                @media(max-width: 700px) {

                    .container {
                        width: 95%;
                    }

                    .card {
                        padding: 20px;
                    }

                    table {
                        font-size: 13px;
                    }

                    th, td {
                        padding: 8px;
                    }
                }

            </style>

            """;
}