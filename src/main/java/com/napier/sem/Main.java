package com.napier.sem;


import java.sql.*;

/**
 * Main application class
 */

public class Main {


    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 20;
        for (int i = 0; i < retries; ++i) {
            System.out.println("🔄 Connecting to database...");
            try {
                // Wait for db to start
                Thread.sleep(5000);
                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );
                System.out.println("✅ Successfully connected!");
                break;
            } catch (SQLException sqle) {
                System.out.println("❌ Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("⚠️ Thread interrupted — should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("🔒 Connection closed.");
            } catch (Exception e) {
                System.out.println("⚠️ Error closing connection to database.");
            }
        }
    }

    /**
     * Get an employee record by ID.
     */
    public Employee getEmployee(int ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, " +
                            "t.title, s.salary, d.dept_name, " +
                            "m.first_name AS manager_first, m.last_name AS manager_last " +
                            "FROM employees e " +
                            "LEFT JOIN titles t ON e.emp_no = t.emp_no " +
                            "LEFT JOIN salaries s ON e.emp_no = s.emp_no " +
                            "LEFT JOIN dept_emp de ON e.emp_no = de.emp_no " +
                            "LEFT JOIN departments d ON de.dept_no = d.dept_no " +
                            "LEFT JOIN dept_manager dm ON d.dept_no = dm.dept_no " +
                            "LEFT JOIN employees m ON dm.emp_no = m.emp_no " +
                            "WHERE e.emp_no = " + ID + " " +
                            "LIMIT 1;";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Return new employee if valid.
            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                emp.dept_name = rset.getString("dept_name");
                emp.manager = rset.getString("manager_first") + " " + rset.getString("manager_last");
                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("❌ Failed to get employee details");
            return null;
        }
    }

    /**
     * Display employee information
     */
    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println("\n📋 Employee Information:");
            System.out.println("-----------------------------");
            System.out.println("ID: " + emp.emp_no);
            System.out.println("Name: " + emp.first_name + " " + emp.last_name);
            System.out.println("Title: " + emp.title);
            System.out.println("Salary: $" + emp.salary);
            System.out.println("Department: " + emp.dept_name);
            System.out.println("Manager: " + emp.manager);
            System.out.println("-----------------------------\n");
        } else {
            System.out.println("⚠️ No employee found.");
        }
    }

    /**
     * Entry point
     */




    public static void main(String[] args) {
        Main a = new Main();

        a.connect();
        Employee emp = a.getEmployee(255530);
        a.displayEmployee(emp);
        a.disconnect();
    }
}