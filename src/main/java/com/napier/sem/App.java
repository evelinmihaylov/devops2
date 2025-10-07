package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            // Load MySQL Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );
                System.out.println("✅ Successfully connected!");
                // Wait a bit to verify
                Thread.sleep(10000);
                break;
            } catch (SQLException sqle) {
                System.out.println("❌ Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted — should not happen.");
            }
        }

        if (con != null) {
            try {
                con.close();
                System.out.println("🔒 Connection closed.");
            } catch (Exception e) {
                System.out.println("Error closing connection to database.");
            }
        }
    }
}