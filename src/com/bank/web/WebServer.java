package com.bank.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.bank.loader.DataLoader;
import com.bank.service.BankService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

    private static BankService bankService;

    public static void main(String[] args) throws Exception {

        // Create BankService
        bankService = new BankService();

        // Load existing accounts
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadAccountsFromFile("Data/accounts.txt", bankService);

        // Create HTTP server on port 8080
        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        // Home page
        server.createContext("/", WebServer::homePage);

        // Start server
        server.start();

        System.out.println("======================================");
        System.out.println(" Bank Account Management Web Server");
        System.out.println("======================================");
        System.out.println("Server started successfully!");
        System.out.println("Open your browser and go to:");
        System.out.println("http://localhost:8080");
        System.out.println("======================================");
    }


    // ==============================
    // HOME PAGE
    // ==============================

    private static void homePage(HttpExchange exchange) throws IOException {

        String html = """
                <!DOCTYPE html>
                <html>

                <head>

                    <meta charset="UTF-8">

                    <title>Bank Account Management System</title>

                    <style>

                        * {
                            box-sizing: border-box;
                        }

                        body {
                            margin: 0;
                            font-family: Arial, sans-serif;
                            background-color: #f4f6f8;
                        }

                        .header {
                            background-color: #1e3a8a;
                            color: white;
                            padding: 25px;
                            text-align: center;
                        }

                        .header h1 {
                            margin: 0;
                        }

                        .header p {
                            margin-top: 8px;
                        }

                        .container {
                            width: 90%;
                            max-width: 1000px;
                            margin: 40px auto;
                        }

                        .menu {
                            display: grid;
                            grid-template-columns: repeat(3, 1fr);
                            gap: 20px;
                        }

                        .card {
                            background-color: white;
                            padding: 25px;
                            border-radius: 10px;
                            text-align: center;
                            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
                        }

                        .card h2 {
                            margin-top: 0;
                            color: #1e3a8a;
                        }

                        .card p {
                            color: #555;
                            min-height: 40px;
                        }

                        .button {
                            display: inline-block;
                            padding: 10px 18px;
                            background-color: #2563eb;
                            color: white;
                            text-decoration: none;
                            border-radius: 6px;
                            margin-top: 10px;
                        }

                        .button:hover {
                            background-color: #1d4ed8;
                        }

                        .footer {
                            text-align: center;
                            margin-top: 40px;
                            padding: 20px;
                            color: #777;
                        }

                        @media (max-width: 800px) {

                            .menu {
                                grid-template-columns: repeat(2, 1fr);
                            }

                        }

                        @media (max-width: 500px) {

                            .menu {
                                grid-template-columns: 1fr;
                            }

                        }

                    </style>

                </head>


                <body>

                    <div class="header">

                        <h1>🏦 Bank Account Management System</h1>

                        <p>Core Java Banking Application</p>

                    </div>


                    <div class="container">

                        <div class="menu">


                            <!-- Open Account -->

                            <div class="card">

                                <h2>Open Account</h2>

                                <p>
                                    Create a new bank account
                                </p>

                                <a href="/open" class="button">
                                    Open Account
                                </a>

                            </div>


                            <!-- Deposit -->

                            <div class="card">

                                <h2>Deposit</h2>

                                <p>
                                    Deposit money into your account
                                </p>

                                <a href="/deposit" class="button">
                                    Deposit
                                </a>

                            </div>


                            <!-- Withdraw -->

                            <div class="card">

                                <h2>Withdraw</h2>

                                <p>
                                    Withdraw money from your account
                                </p>

                                <a href="/withdraw" class="button">
                                    Withdraw
                                </a>

                            </div>


                            <!-- Transfer -->

                            <div class="card">

                                <h2>Transfer Funds</h2>

                                <p>
                                    Transfer money using account number
                                </p>

                                <a href="/transfer" class="button">
                                    Transfer
                                </a>

                            </div>


                            <!-- UPI -->

                            <div class="card">

                                <h2>UPI Transfer</h2>

                                <p>
                                    Send money using mobile number
                                </p>

                                <a href="/upi" class="button">
                                    Send via UPI
                                </a>

                            </div>


                            <!-- Account Details -->

                            <div class="card">

                                <h2>Account Details</h2>

                                <p>
                                    View account information
                                </p>

                                <a href="/account" class="button">
                                    View Account
                                </a>

                            </div>


                            <!-- Transaction History -->

                            <div class="card">

                                <h2>Transactions</h2>

                                <p>
                                    View transaction history
                                </p>

                                <a href="/transactions" class="button">
                                    View Transactions
                                </a>

                            </div>


                            <!-- All Accounts -->

                            <div class="card">

                                <h2>All Accounts</h2>

                                <p>
                                    View all bank accounts
                                </p>

                                <a href="/accounts" class="button">
                                    View Accounts
                                </a>

                            </div>


                            <!-- Close Account -->

                            <div class="card">

                                <h2>Close Account</h2>

                                <p>
                                    Close an existing bank account
                                </p>

                                <a href="/close" class="button">
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


    // ==============================
    // SEND HTML RESPONSE
    // ==============================

    private static void sendResponse(
            HttpExchange exchange,
            String html) throws IOException {

        byte[] response =
                html.getBytes(StandardCharsets.UTF_8);

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
}