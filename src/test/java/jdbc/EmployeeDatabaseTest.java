package jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}

