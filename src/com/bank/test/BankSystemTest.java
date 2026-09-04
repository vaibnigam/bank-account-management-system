package com.bank.test;

import java.util.List;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.BankService;

public class BankSystemTest {

    private static int passed = 0;
    private static int failed = 0;


    public static void main(String[] args) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("     BANK SYSTEM TEST SUITE");
        System.out.println("======================================");
        System.out.println();


        BankService bankService = new BankService();


        // ==========================================
        // TEST 1 - OPEN ACCOUNT
        // ==========================================

        System.out.println("TEST 1: Open Account");

        Account account1 =
                bankService.openAccount(
                        "Test User 1",
                        "9876543210",
                        5000
                );

        assertNotNull(
                "Account should be created",
                account1
        );


        // ==========================================
        // TEST 2 - INVALID MOBILE
        // ==========================================

        System.out.println("TEST 2: Invalid Mobile");

        Account invalidAccount =
                bankService.openAccount(
                        "Test User 2",
                        "1234567890",
                        5000
                );

        assertNull(
                "Account should NOT be created",
                invalidAccount
        );


        // ==========================================
        // TEST 3 - INVALID BALANCE
        // ==========================================

        System.out.println("TEST 3: Negative Balance");

        Account negativeBalanceAccount =
                bankService.openAccount(
                        "Test User 3",
                        "9876543211",
                        -100
                );

        assertNull(
                "Negative balance should be rejected",
                negativeBalanceAccount
        );


        // ==========================================
        // TEST 4 - DEPOSIT
        // ==========================================

        System.out.println("TEST 4: Deposit");

        boolean depositResult =
                bankService.deposit(
                        account1.getAccountNumber(),
                        2000
                );

        assertTrue(
                "Deposit should succeed",
                depositResult
        );


        Account afterDeposit =
                bankService.getAccountDetails(
                        account1.getAccountNumber()
                );

        assertEquals(
                "Balance after deposit",
                7000,
                afterDeposit.getBalance()
        );


        // ==========================================
        // TEST 5 - WITHDRAW
        // ==========================================

        System.out.println("TEST 5: Withdraw");

        boolean withdrawResult =
                bankService.withdraw(
                        account1.getAccountNumber(),
                        1000
                );

        assertTrue(
                "Withdrawal should succeed",
                withdrawResult
        );


        Account afterWithdraw =
                bankService.getAccountDetails(
                        account1.getAccountNumber()
                );

        assertEquals(
                "Balance after withdrawal",
                6000,
                afterWithdraw.getBalance()
        );


        // ==========================================
        // TEST 6 - WITHDRAW MORE THAN BALANCE
        // ==========================================

        System.out.println(
                "TEST 6: Insufficient Balance"
        );

        boolean excessiveWithdraw =
                bankService.withdraw(
                        account1.getAccountNumber(),
                        10000
                );

        assertFalse(
                "Withdrawal should fail",
                excessiveWithdraw
        );


        // ==========================================
        // TEST 7 - CREATE SECOND ACCOUNT
        // ==========================================

        System.out.println("TEST 7: Create Second Account");

        Account account2 =
                bankService.openAccount(
                        "Test User 2",
                        "9876543212",
                        3000
                );

        assertNotNull(
                "Second account should be created",
                account2
        );


        // ==========================================
        // TEST 8 - TRANSFER
        // ==========================================

        System.out.println("TEST 8: Account Transfer");

        boolean transferResult =
                bankService.transfer(
                        account1.getAccountNumber(),
                        account2.getAccountNumber(),
                        1000
                );

        assertTrue(
                "Transfer should succeed",
                transferResult
        );


        Account sender =
                bankService.getAccountDetails(
                        account1.getAccountNumber()
                );

        Account receiver =
                bankService.getAccountDetails(
                        account2.getAccountNumber()
                );


        assertEquals(
                "Sender balance after transfer",
                5000,
                sender.getBalance()
        );


        assertEquals(
                "Receiver balance after transfer",
                4000,
                receiver.getBalance()
        );


        // ==========================================
        // TEST 9 - UPI TRANSFER
        // ==========================================

        System.out.println("TEST 9: UPI Transfer");

        boolean upiResult =
                bankService.transferByMobile(
                        "9876543210",
                        "9876543212",
                        500
                );

        assertTrue(
                "UPI transfer should succeed",
                upiResult
        );


        sender =
                bankService.getAccountDetails(
                        account1.getAccountNumber()
                );

        receiver =
                bankService.getAccountDetails(
                        account2.getAccountNumber()
                );


        assertEquals(
                "Sender balance after UPI",
                4500,
                sender.getBalance()
        );


        assertEquals(
                "Receiver balance after UPI",
                4500,
                receiver.getBalance()
        );


        // ==========================================
        // TEST 10 - ACCOUNT DETAILS
        // ==========================================

        System.out.println("TEST 10: Account Details");

        Account details =
                bankService.getAccountDetails(
                        account1.getAccountNumber()
                );

        assertNotNull(
                "Account should be found",
                details
        );


        // ==========================================
        // TEST 11 - TRANSACTION HISTORY
        // ==========================================

        System.out.println(
                "TEST 11: Transaction History"
        );

        List<Transaction> history =
                bankService.getTransactionHistory(
                        account1.getAccountNumber()
                );


        assertTrue(
                "Transaction history should not be empty",
                !history.isEmpty()
        );


        // ==========================================
        // TEST 12 - ALL ACCOUNTS
        // ==========================================

        System.out.println("TEST 12: Get All Accounts");

        List<Account> accounts =
                bankService.getAllAccounts();


        assertTrue(
                "Accounts list should contain accounts",
                accounts.size() >= 2
        );


        // ==========================================
        // TEST 13 - CLOSE ACCOUNT WITH BALANCE
        // ==========================================

        System.out.println(
                "TEST 13: Close Account With Balance"
        );

        boolean closeWithBalance =
                bankService.closeAccount(
                        account1.getAccountNumber()
                );


        assertFalse(
                "Account with balance should not close",
                closeWithBalance
        );


        // ==========================================
        // FINAL RESULT
        // ==========================================

        System.out.println();
        System.out.println("======================================");
        System.out.println("          TEST RESULT");
        System.out.println("======================================");

        System.out.println(
                "Passed : " + passed
        );

        System.out.println(
                "Failed : " + failed
        );

        System.out.println(
                "Total  : " + (passed + failed)
        );


        if (failed == 0) {

            System.out.println();
            System.out.println(
                    "🎉 ALL TESTS PASSED!"
            );

        } else {

            System.out.println();
            System.out.println(
                    "❌ SOME TESTS FAILED!"
            );
        }


        System.out.println(
                "======================================"
        );
    }


    // ============================================================
    // ASSERT METHODS
    // ============================================================

    private static void assertTrue(
            String testName,
            boolean condition) {

        if (condition) {

            passed++;

            System.out.println(
                    "   ✅ PASS - " + testName
            );

        } else {

            failed++;

            System.out.println(
                    "   ❌ FAIL - " + testName
            );
        }
    }


    private static void assertFalse(
            String testName,
            boolean condition) {

        assertTrue(
                testName,
                !condition
        );
    }


    private static void assertNotNull(
            String testName,
            Object object) {

        assertTrue(
                testName,
                object != null
        );
    }


    private static void assertNull(
            String testName,
            Object object) {

        assertTrue(
                testName,
                object == null
        );
    }


    private static void assertEquals(
            String testName,
            double expected,
            double actual) {

        if (Double.compare(expected, actual) == 0) {

            passed++;

            System.out.println(
                    "   ✅ PASS - "
                    + testName
                    + " | Expected: "
                    + expected
                    + " | Actual: "
                    + actual
            );

        } else {

            failed++;

            System.out.println(
                    "   ❌ FAIL - "
                    + testName
                    + " | Expected: "
                    + expected
                    + " | Actual: "
                    + actual
            );
        }
    }
}