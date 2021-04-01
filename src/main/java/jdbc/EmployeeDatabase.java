package jdbc;


import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
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
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println(" " + driverClass.getClass().getName());
        }
    }

    public List<PayrollService> readData() {
        String Sql_Query = "select * from employee_payroll";
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

    public long insertRecord(int id, String name, String date, String gender, double salary) {
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(id,name,start_date,gender,salary) values(?,?,?,?,?); ");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, String.valueOf(Date.valueOf(date)));
            preparedStatement.setString(4, gender);
            preparedStatement.setDouble(5, salary);
            int resultSet = preparedStatement.executeUpdate();
            System.out.println(resultSet);
            return resultSet;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public long updateRecord(double salary, int id) {
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Update employee_payroll set salary=? where id=? ; ");
            preparedStatement.setDouble(1, salary);
            preparedStatement.setInt(2, id);
            long resultSet = preparedStatement.executeUpdate();
            System.out.println(resultSet);
            return resultSet;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<PayrollService> payrollData(String Date) {
        String Sql_Query = "select * from employee_payroll where start_date>=?";
        List<PayrollService> payrollServiceData = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Sql_Query);
            preparedStatement.setDate(1, java.sql.Date.valueOf(Date));
            ResultSet resultSet = preparedStatement.executeQuery();

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
                System.out.println("Gender=" + Gender);
                System.out.println("Salary=" + Salary);


                PayrollService payrollServiceData1 = new PayrollService(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4), resultSet.getInt(5));
                payrollServiceData.add(payrollServiceData1);


            }
            preparedStatement.close();
            connection.close();


        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return payrollServiceData;
    }

    public List<String> datafunction() {
        List<String> list = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select gender,sum(salary), avg(salary),min(salary),max(salary),count(salary) from employee_payroll group by gender");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                System.out.println("Gender: " + resultSet.getString(1));
                System.out.println("Salary: " + resultSet.getString(2));
                for (int i = 0; i < 10; i++) {
                    if (index < 5) {
                        list.add(i, resultSet.getString(index));
                        index++;
                    }
                }
                System.out.println(list);
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int payrollDetails(int payroll_id, double basicpay, double deduction, double taxpay, double tax, double netpay) {
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into payroll_details(payroll_id,basicpay,deduction,taxpay,tax,netpay) values(?,?,?,?,?,?); ");

            preparedStatement.setInt(1, payroll_id);
            preparedStatement.setDouble(2, basicpay);
            preparedStatement.setDouble(3, deduction);
            preparedStatement.setDouble(4, taxpay);
            preparedStatement.setDouble(5, tax);
            preparedStatement.setDouble(6, netpay);
            int resultSet = preparedStatement.executeUpdate();
            return resultSet;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void bothTables(int id, String name, String date,String gender,double salary,int payroll_id) throws IllegalAccessException, SQLException {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(id,name,start_date,gender,salary) values(?,?,?,?,?); ");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, String.valueOf(Date.valueOf(date)));
            preparedStatement.setString(4, gender);
            preparedStatement.setDouble(5, salary);
            int resultSet = preparedStatement.executeUpdate();


            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into payroll_details(payroll_id,basicpay,deduction,taxpay,tax,netpay) values(?,?,?,?,?,?);");
            preparedStatement1.setInt(1, payroll_id);
            preparedStatement1.setDouble(2, salary / 20);
            preparedStatement1.setDouble(3, salary / 5);
            preparedStatement1.setDouble(4, salary / 2);
            preparedStatement1.setDouble(5, salary / 20);
            preparedStatement1.setDouble(6, salary / 10);
            int resultSet1 = preparedStatement1.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
    }

    public void insertIntoTables(int id, String name, String date,String gender,double salary,int payroll_id) throws IllegalAccessException, SQLException {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(id,name,start_date,gender,salary) values(?,?,?,?,?); ");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, String.valueOf(Date.valueOf(date)));
            preparedStatement.setString(4, gender);
            preparedStatement.setDouble(5, salary);
            int resultSet = preparedStatement.executeUpdate();


            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into payroll_details(payroll_id,basicpay,deduction,taxpay,tax,netpay) values(?,?,?,?,?,?); ");
            preparedStatement1.setInt(1, payroll_id);
            preparedStatement1.setDouble(2, salary / 20);
            preparedStatement1.setDouble(3, salary / 5);
            preparedStatement1.setDouble(4, salary / 2);
            preparedStatement1.setDouble(5, salary / 20);
            preparedStatement1.setDouble(6, salary / 10);
            int resultSet1 = preparedStatement1.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }

    }
    public void deleteRecord(String name) throws SQLException, IllegalAccessException {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from employee_payroll where name=?; ");
            preparedStatement.setString(1, name);
            int resultSet = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
    }

    public void insetRecordArrays(List<PayrollService> payrollServiceData) throws SQLException, IllegalAccessException {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(id,name,start_date,gender,salary) values(?,?,?,?,?); ");
            for (Iterator<PayrollService> iterator = payrollServiceData.iterator(); iterator.hasNext(); ) {
                PayrollService payrollServiceData1 = (PayrollService) iterator.next();
                System.out.println("employee being added  " + payrollServiceData1.getName());
                preparedStatement.setInt(1, payrollServiceData1.getId());
                preparedStatement.setString(2, payrollServiceData1.getName());
                preparedStatement.setDate(3, payrollServiceData1.getStartDate());
                preparedStatement.setString(4, payrollServiceData1.getGender());
                preparedStatement.setDouble(5, payrollServiceData1.getSalary());
                System.out.println("employee Added  " + payrollServiceData1.Name);
                preparedStatement.addBatch();
            }
            int[] recordUpdateCounts = preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
    }

}

