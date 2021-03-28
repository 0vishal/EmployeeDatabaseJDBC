package jdbc;


import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeeDatabase {

        Connection getConnection() throws IllegalAccessException {
            String JDBCURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
            String UserName = "root";
            String Password = "root";
            Connection connection = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Driver loaded");
            } catch (ClassNotFoundException e) {
                throw new IllegalAccessException(String.format("Driver not found in classpath%s", e));

            }

            listDrivers();
            try {
                System.out.println("Connecting to database" + JDBCURL);
                connection = DriverManager.getConnection(JDBCURL, UserName, Password);
                System.out.println("Connection succesfully" + connection);
            } catch (Exception e) {
                e.printStackTrace();

            }
            return connection;

        }
        private static void listDrivers() {
            Enumeration<Driver> driverList = DriverManager.getDrivers();
            while(driverList.hasMoreElements()){
                Driver driverClass = driverList.nextElement();
                System.out.println(" "+driverClass.getClass().getName() );
            }
        }


    }

