package Src;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeDatabaseManagmentSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeeCURD";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EmployeeDatabaseManagmentSystem employeeCRUD = new EmployeeDatabaseManagmentSystem();
        employeeCRUD.run();
    }

    private void run() {
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee Salary");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");

            StringBuffer choiceBuffer = new StringBuffer();
            System.out.print("Enter your choice: ");
            choiceBuffer.append(scanner.nextLine());

            try {
                int choice = Integer.parseInt(choiceBuffer.toString());

                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        getAllEmployees();
                        break;
                    case 3:
                        updateEmployeeSalary();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        scanner.close();
    }

    public void addEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        double salary = getValidSalary();

        String query = "INSERT INTO Employees (name, department, salary) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, department);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private double getValidSalary() {
        double salary = -1;
        while (salary < 0) {
            try {
                System.out.print("Enter salary: ");
                salary = scanner.nextDouble();
                if (salary < 0) {
                    System.out.println("Salary cannot be negative. Please try again.");
                }
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
        return salary;
    }

    public void getAllEmployees() {
        String query = "SELECT * FROM Employees";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("All Employees:");
            System.out.println("-------------------------------------------------------------------");5
            System.out.printf("%-5s %-20s %-15s %-8s%n", "ID", "Name", "Department", "Salary");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");
                System.out.printf("%-5d %-20s %-15s %-7.2f%n", id, name, department, salary);
            }
            System.out.println("-------------------------------------------------------------------");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void updateEmployeeSalary() {
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        double newSalary = getValidSalary();

        String query = "UPDATE Employees SET salary = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee salary updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String query = "DELETE FROM Employees WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
}