package jdbc;

import org.junit.jupiter.api.Test;

public class EmployeeDatabaseTest {
    EmployeeDatabase employeeDatabase;

    @Test
    public void checkconnection() throws IllegalAccessException {
        employeeDatabase = new EmployeeDatabase();
        employeeDatabase.getConnection();
    }}
