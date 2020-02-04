package com.epam.brest;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class App {
    private static final int ENTER_DISTANCE = 0;
    private static final int ENTER_WEIGHT = 1;
    private static final String EXIT_VALUE = "Q";
    private static double pricePerKm, pricePerKg;

    static {
        try {
            FileInputStream configPrices = new FileInputStream("src/main/resources/prices.properties");
            Properties prices = new Properties();
            Reader reader = new InputStreamReader(configPrices);
            prices.load(reader);
            pricePerKm = Double.parseDouble(prices.getProperty("price_per_km"));
            pricePerKg = Double.parseDouble(prices.getProperty("price_per_kg"));
        } catch (FileNotFoundException e) {
            System.out.println("Error! Couldn't find file config");
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.out.print("Error! Something goes wrong :D");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main( String[] args ) {
        double distance=0.0, weight=0.0;
        int idOperation = 0;

        System.out.printf("System info:\npricePerKm= %f\npricePerKg= %f\n\n", pricePerKm, pricePerKg);
        System.out.println("For exit use: Ctrl + C");
        Scanner scanner = new Scanner(System.in);
        String inputValue;
        do {
            if (idOperation == ENTER_DISTANCE) {
                System.out.print("Distance : ");
                inputValue = scanner.next();
                if (isExitValue(inputValue)) {
                    if (isCorrectDoubleValue(inputValue)) {
                        distance = Double.parseDouble(inputValue);
                    }
                }
            } else {
                System.out.print("Weight : ");
                inputValue = scanner.next();
                if (isExitValue(inputValue)) {
                    if (isCorrectDoubleValue(inputValue)) {
                        weight = Double.parseDouble(inputValue);
                    }
                }
            }

            if (idOperation == ENTER_WEIGHT) {
                double calcResult = distance * pricePerKm + weight * pricePerKg;
                System.out.printf("Price: %f $\n", calcResult);
                idOperation = ENTER_DISTANCE;
            } else {
                idOperation++;
            }
        } while (isExitValue(inputValue));

        System.out.println("Finish!");
    }

    private static boolean checkValue(String value) {
        return value.equalsIgnoreCase(EXIT_VALUE);
    }
    private static boolean isExitValue(String value) {
        return !value.equalsIgnoreCase("Q");
    }

    private static boolean isCorrectDoubleValue(String value) {
        boolean checkResult;
        try {
            double enteredDoubleValue = Double.parseDouble(value);
            checkResult = enteredDoubleValue >= 0;
        } catch (NumberFormatException e) {
            checkResult = false;
        }
        return checkResult;
    }
}
