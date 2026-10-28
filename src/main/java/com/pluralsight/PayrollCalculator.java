package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PayrollCalculator {
    public static void main(String[] args) {
        String fileName = "src/main/resources/employees.csv";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader buffReader = new BufferedReader(fileReader);

            buffReader.readLine(); // Пропускаем заголовок
            String line = buffReader.readLine();

            while (line != null) {
                String[] tokens = line.split("\\|");

                int employeeId = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee employee = new Employee(
                        employeeId, name, hoursWorked, payRate
                );

                System.out.printf("%d | %s | $%.2f%n",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getGrossPay());

                line = buffReader.readLine();
            }

            buffReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);

        } catch (IOException e) {
            System.out.println("Problem reading the file: "
                    + e.getMessage());
        }
    }
}