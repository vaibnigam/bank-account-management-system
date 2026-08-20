package com.bank.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.bank.service.BankService;

public class DataLoader {

    public void loadAccountsFromFile(String filePath, BankService bankService) {
        try {
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);

            int loadedCount = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length != 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String accountNumber = parts[0].trim();
                String holderName = parts[1].trim();
                String mobileNumber = parts[2].trim();
                double balance = Double.parseDouble(parts[3].trim());

                bankService.loadAccountDirectly(accountNumber, holderName, mobileNumber, balance);
                loadedCount++;
            }

            fileScanner.close();
            System.out.println(loadedCount + " accounts loaded successfully from file.");

        } catch (FileNotFoundException e) {
            System.out.println("Accounts data file not found. Starting with no accounts.");
        }
    }
}