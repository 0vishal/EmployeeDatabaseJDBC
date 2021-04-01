package jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabaseTest {
    EmployeeDatabase employeeDatabase;

    @Test
    void readData() {
        employeeDatabase = new EmployeeDatabase();
        List<PayrollService> payrollServiceDataList = employeeDatabase.readData();
        Assertions.assertEquals(2, payrollServiceDataList.size());
    }

    @Test
    void update_Record() {
        double salary = 600000;
        int id = 1;
        employeeDatabase = new EmployeeDatabase();
        long result = employeeDatabase.updateRecord(salary, id);
        Assertions.assertEquals(2, result);
    }

    @Test
    public void dataFromRange() {
        String date = "2020-01-01";
        employeeDatabase = new EmployeeDatabase();
        List<PayrollService> employeePayrollDataList = employeeDatabase.payrollData(date);
        Assertions.assertEquals(2, employeePayrollDataList.size());
    }

    @Test
    public void functions(){
        employeeDatabase=new EmployeeDatabase();
        List<String> list=employeeDatabase.datafunction();
        Assertions.assertEquals(4,list.size());
    }

    @Test
    public void newrecord(){
        int id=9;
        String name="Karan";
        String date="2021-2-5";
        String gender="M";
        double salary=80000;
        employeeDatabase=new EmployeeDatabase();
        employeeDatabase.insertRecord(id,name,date,gender,salary);
        List<PayrollService> payrollServiceDataList=employeeDatabase.readData();
        Assertions.assertEquals(9,payrollServiceDataList.size());
    }

    @Test
    public void payroll_details() {
        int payroll_id=1;
        double basicpay=35000;
        double deduction=10000;
        double taxpay=1000;
        double tax=500;
        double netpay=15000;

        employeeDatabase=new EmployeeDatabase();
        employeeDatabase.payrollDetails(payroll_id,basicpay,deduction,taxpay,tax,netpay);
        List<PayrollService>payrollServiceDataList=employeeDatabase.readData();
        Assertions.assertEquals(1,payrollServiceDataList.size());
    }

    @Test
    void insertBothTables() throws SQLException, IllegalAccessException {
        int id=10;
        String name="Vinod";
        String date="2021-2-10";
        String gender="M";
        double salary=50000;
        int payroll_id=10;

       employeeDatabase=new EmployeeDatabase();
        employeeDatabase.bothTables(id,name,date,gender,salary,payroll_id);
        List<PayrollService>payrollServiceDataList=employeeDatabase.readData();
        Assertions.assertEquals(10,payrollServiceDataList.size());
    }

    @Test
    void insertintoTables() throws SQLException, IllegalAccessException {
        int id=11;
        String name="Sarvesh";
        String date="2020-3-08";
        String gender="M";
        double salary=50000;
        int payroll_id=11;

         employeeDatabase=new EmployeeDatabase();
        employeeDatabase.insertIntoTables(id,name,date,gender,salary,payroll_id);
        List<PayrollService>payrollServiceDataList=employeeDatabase.readData();
        Assertions.assertEquals(11,payrollServiceDataList.size());
    }

    @Test
    public void deletingemployee() throws SQLException, IllegalAccessException {
        String name="vishal";
        employeeDatabase=new EmployeeDatabase();
        employeeDatabase.deleteRecord(name);
        List<PayrollService> employeePayrollDataList=employeeDatabase.readData();
        Assertions.assertEquals(3,employeePayrollDataList.size());
    }

    @Test

    public void insert_multiple_values() throws SQLException, IllegalAccessException, SQLException {
       employeeDatabase=new EmployeeDatabase();
        List<PayrollService> list=new ArrayList<>();
        list.add(new PayrollService(12,"Ravi", Date.valueOf("2020-04-21"),"M",34000));
        list.add(new PayrollService(13,"Shree",Date.valueOf("2021-06-12"),"F",54000));
        employeeDatabase.insetRecordArrays(list);
        List<PayrollService> employeePayrollDataList=employeeDatabase.readData();
        Assertions.assertEquals(7,employeePayrollDataList.size());

    }

    @Test
    public void addmultipleecords_calculateduration() throws SQLException, IllegalAccessException,SQLException {
       employeeDatabase=new EmployeeDatabase();
        List<PayrollService> list=new ArrayList<>();
        list.add(new PayrollService(14,"Nahir", Date.valueOf("2019-01-21"),"M",50000));
        list.add(new PayrollService(15,"Arya",Date.valueOf("2018-09-15"),"F",90000));

        Instant start = Instant.now();
        employeeDatabase.insetRecordArrays(list);
        Instant end = Instant.now();
        System.out.println("Duration without thread"+ Duration.between(start,end));
        List<PayrollService> employeePayrollDataList=employeeDatabase.readData();
        Assertions.assertEquals(15,employeePayrollDataList.size());

    }

    @Test
    public void addrecordsthread() throws SQLException, IllegalAccessException,SQLException {
       employeeDatabase=new EmployeeDatabase();
        List<PayrollService> list=new ArrayList<>();
        list.add(new PayrollService(14,"Ranbir", Date.valueOf("2021-03-01"),"M",70000));
        list.add(new PayrollService(15,"Naina",Date.valueOf("2021-01-11"),"F",90000));
        list.add(new PayrollService(16,"yami",Date.valueOf("2021-06-21"),"F",90000));
        Instant start =Instant.now();
        employeeDatabase.insetRecordsThread(list);
        Instant end = Instant.now();
        System.out.println("Duration without thread: "+ Duration.between(start,end));

        Instant thredstart =Instant.now();
        employeeDatabase.insetRecordsThread(list);
        Instant threadend = Instant.now();
        System.out.println("Duration with thread: "+ Duration.between(thredstart,threadend));

        List<PayrollService> employeePayrollDataList=employeeDatabase.readData();
        Assertions.assertEquals(3,employeePayrollDataList.size());

    }





}
