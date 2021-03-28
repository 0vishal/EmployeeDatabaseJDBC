package jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeDatabaseTest {
    EmployeeDatabase employeeDatabase;

    @Test
    void readData() {
        employeeDatabase=new EmployeeDatabase();
        List<PayrollService> payrollServiceDataList=employeeDatabase.readData();
        Assertions.assertEquals(2,payrollServiceDataList.size());
    }

    @Test
    void update_Record(){
        double salary=600000;
        int id=1;
        employeeDatabase=new EmployeeDatabase();
        long result=employeeDatabase.updateRecord(salary,id);
        Assertions.assertEquals(2,result);
    }
}

