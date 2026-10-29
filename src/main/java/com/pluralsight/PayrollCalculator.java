package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class PayrollCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the file to process: ");
        String inputName = scanner.nextLine().trim();

        System.out.print("Enter the name of the payroll file to create: ");
        String outputName = scanner.nextLine().trim();

        String inputPath = "src/main/resources/" + inputName;
        String outputPath = "src/main/resources/" + outputName;

        if (inputPath.equals(outputPath)) {
            System.out.println("Please use a different output file name.");
            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(new FileReader(inputPath));

                BufferedWriter writer =
                        new BufferedWriter(new FileWriter(outputPath))
        ) {
            writer.write("id|name|gross pay");
            writer.newLine();

            reader.readLine(); // Skip the header
            String line = reader.readLine();

            while (line != null) {
                String[] tokens = line.split("\\|");

                int employeeId = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee employee = new Employee(
                        employeeId, name, hoursWorked, payRate
                );

                String reportLine = String.format(
                        Locale.US,
                        "%d|%s|%.2f",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getGrossPay()
                );

                writer.write(reportLine);
                writer.newLine();

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not open the file: "
                    + e.getMessage());
            return;

        } catch (IOException e) {
            System.out.println("Problem reading or writing the file: "
                    + e.getMessage());
            return;
        }

        System.out.println("Payroll report saved to: " + outputPath);
    }
}