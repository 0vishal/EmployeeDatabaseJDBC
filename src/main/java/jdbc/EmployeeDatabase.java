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

        public List<PayrollService> readData() {
        String Sql_Query = "select * from employee_payroll"
        List<PayrollService> payrollServiceData = new ArrayList<PayrollService>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Sql_Query);

            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                String Name = resultSet.getString(2);
                Date StartDate = resultSet.getDate(3);
                String Gender = resultSet.getString(4);
                int Salary = resultSet.getInt(5);

                System.out.println();
                System.out.println("id=" + id);
                System.out.println("Name=" + Name);
                System.out.println("StartDate=" + StartDate);
                System.out.println("Salary=" + Salary);

                PayrollService payrollServiceData1 = new PayrollService(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4), resultSet.getInt(5));
                payrollServiceData.add(payrollServiceData1);


            }
            statement.close();
            connection.close();


        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return payrollServiceData;
    }




    public long updateRecord(double salary,int id){
            try {
                    Connection connection=this.getConnection();
                    PreparedStatement preparedStatement=connection.prepareStatement("Update employee_payroll set salary=? where id=? ; ");
                    preparedStatement.setDouble(1,salary);
                    preparedStatement.setInt(2,id);
                    long resultSet=preparedStatement.executeUpdate();
                    System.out.println(resultSet);
                    return resultSet;
                } catch (SQLException | IllegalAccessException e){
                    e.printStackTrace();
                }
                return 0;
            }