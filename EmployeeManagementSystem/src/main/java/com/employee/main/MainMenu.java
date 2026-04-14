package com.employee.main;

import com.employee.dao.DepartmentDAO;
import com.employee.dao.EmployeeDAO;
import com.employee.entity.Department;
import com.employee.entity.Employee;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    static Scanner scanner = new Scanner(System.in);
    static DepartmentDAO departmentDAO = new DepartmentDAO();
    static EmployeeDAO employeeDAO = new EmployeeDAO();

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   Employee Management System (JPA)     ");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> departmentMenu();
                case 2 -> employeeMenu();
                case 3 -> {
                    System.out.println("Goodbye!");
                    JPAUtil.shutdown();
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    // ─── Main Menu ───────────────────────────────────────────────

    static void printMainMenu() {
        System.out.println("\n─── Main Menu ───");
        System.out.println("1. Department Management");
        System.out.println("2. Employee Management");
        System.out.println("3. Exit");
    }

    // ─── Department Menu ─────────────────────────────────────────

    static void departmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n─── Department Menu ───");
            System.out.println("1. Add Department");
            System.out.println("2. View All Departments");
            System.out.println("3. Update Department");
            System.out.println("4. Delete Department");
            System.out.println("5. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");
            switch (choice) {
                case 1 -> addDepartment();
                case 2 -> viewAllDepartments();
                case 3 -> updateDepartment();
                case 4 -> deleteDepartment();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addDepartment() {
        System.out.println("\n── Add Department ──");
        String name = getStringInput("Department name: ");
        String location = getStringInput("Location: ");
        departmentDAO.addDepartment(new Department(name, location));
    }

    static void viewAllDepartments() {
        System.out.println("\n── All Departments ──");
        List<Department> list = departmentDAO.getAllDepartments();
        if (list.isEmpty()) {
            System.out.println("No departments found.");
        } else {
            list.forEach(d -> System.out.println(
                "  [" + d.getId() + "] " + d.getName() + " — " + d.getLocation()
            ));
        }
    }

    static void updateDepartment() {
        System.out.println("\n── Update Department ──");
        viewAllDepartments();
        int id = getIntInput("Enter department ID to update: ");
        String name = getStringInput("New name: ");
        String location = getStringInput("New location: ");
        departmentDAO.updateDepartment(id, name, location);
    }

    static void deleteDepartment() {
        System.out.println("\n── Delete Department ──");
        viewAllDepartments();
        int id = getIntInput("Enter department ID to delete: ");
        departmentDAO.deleteDepartment(id);
    }

    // ─── Employee Menu ────────────────────────────────────────────

    static void employeeMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n─── Employee Menu ───");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. View Employee by ID");
            System.out.println("4. View Employees by Department");
            System.out.println("5. Update Employee");
            System.out.println("6. Delete Employee");
            System.out.println("7. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAllEmployees();
                case 3 -> viewEmployeeById();
                case 4 -> viewByDepartment();
                case 5 -> updateEmployee();
                case 6 -> deleteEmployee();
                case 7 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addEmployee() {
        System.out.println("\n── Add Employee ──");
        String firstName = getStringInput("First name: ");
        String lastName = getStringInput("Last name: ");
        String email = getStringInput("Email: ");
        double salary = getDoubleInput("Salary: ");

        viewAllDepartments();
        int deptId = getIntInput("Enter department ID: ");
        Department dept = departmentDAO.getDepartmentById(deptId);

        if (dept == null) {
            System.out.println("Invalid department. Employee not added.");
            return;
        }
        employeeDAO.addEmployee(new Employee(firstName, lastName, email, salary, dept));
    }

    static void viewAllEmployees() {
        System.out.println("\n── All Employees ──");
        List<Employee> list = employeeDAO.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            list.forEach(e -> System.out.println(
                "  [" + e.getId() + "] " +
                e.getFirstName() + " " + e.getLastName() +
                " | " + e.getEmail() +
                " | Salary: " + e.getSalary() +
                " | Dept: " + (e.getDepartment() != null ? e.getDepartment().getName() : "None")
            ));
        }
    }

    static void viewEmployeeById() {
        System.out.println("\n── View Employee by ID ──");
        int id = getIntInput("Enter employee ID: ");
        Employee emp = employeeDAO.getEmployeeById(id);
        if (emp != null) System.out.println(emp);
    }

    static void viewByDepartment() {
        System.out.println("\n── Employees by Department ──");
        viewAllDepartments();
        int deptId = getIntInput("Enter department ID: ");
        List<Employee> list = employeeDAO.getEmployeesByDepartment(deptId);
        if (list.isEmpty()) {
            System.out.println("No employees in this department.");
        } else {
            list.forEach(e -> System.out.println(
                "  [" + e.getId() + "] " + e.getFirstName() + " " + e.getLastName() +
                " | " + e.getEmail() +
                " | Salary: " + e.getSalary()
            ));
        }
    }

    static void updateEmployee() {
        System.out.println("\n── Update Employee ──");
        viewAllEmployees();
        int id = getIntInput("Enter employee ID to update: ");
        String firstName = getStringInput("New first name: ");
        String lastName = getStringInput("New last name: ");
        String email = getStringInput("New email: ");
        double salary = getDoubleInput("New salary: ");
        viewAllDepartments();
        int deptId = getIntInput("Enter new department ID: ");
        employeeDAO.updateEmployee(id, firstName, lastName, email, salary, deptId);
    }

    static void deleteEmployee() {
        System.out.println("\n── Delete Employee ──");
        viewAllEmployees();
        int id = getIntInput("Enter employee ID to delete: ");
        employeeDAO.deleteEmployee(id);
    }

    // ─── Input Helpers ────────────────────────────────────────────

    static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}