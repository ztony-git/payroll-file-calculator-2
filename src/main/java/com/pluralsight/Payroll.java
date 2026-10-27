package com.pluralsight;

import java.io.*;
import java.util.Scanner;

/**
 * Payroll (Workbook 3a, Section 1-2 Using BufferedReader, Exercise 3, Step 2)
 *
 * Reads employees.csv one line at a time. Each line looks like
 *   10|Dana Wyatt|52.5|12.50       (id|name|hours-worked|pay-rate)
 * The line is split on | into its four values, turned into an Employee object,
 * and the employee is printed with their gross pay (hours x rate).
 *
 * The first line of employees.csv is a header (id|name|hours-worked|pay-rate),
 * not an employee, so it is read once and skipped before the loop.
 * employees.csv sits in the project folder, next to pom.xml.
 */
public class Payroll {
    public static void main(String[] args) {
        String fileName = "employees.csv";

        // Reading a file can throw an IOException, so the work happens inside try
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the file to process: ");
            String fileRead = scanner.nextLine();
            System.out.print("Enter the name of the payroll file to create: ");
            String fileWrite = scanner.nextLine();

            // FileReader opens the file; BufferedReader wraps it so we can read whole lines
            BufferedReader reader = new BufferedReader(new FileReader(fileRead));
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileWrite));

            String line;

            // Skip the header line: "id" is not a number and parseInt would fail on it
            reader.readLine();

            // readLine() returns null at the end of the file, which ends the loop
            while ((line = reader.readLine()) != null) {
                // | has a special meaning in split (it takes a pattern), so it is escaped as \\|
                String[] tokens = line.split("\\|");
                // Everything read from a file is text: convert each value to its real type
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee employee = new Employee(id, name, hoursWorked, payRate);

                writer.write(employee.getEmployeeId() + "|" + employee.getName() + "|" + employee.getGrossPay() + "\n");
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            // Most likely cause: employees.csv is not in the project folder
            System.err.println("Error reading file: " + fileName);
        }
    }
}