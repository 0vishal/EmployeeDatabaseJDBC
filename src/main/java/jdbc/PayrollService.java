package jdbc;

import java.sql.Date;

public class PayrollService {

        public int id;
        public String Name;
        public Date StartDate;
        public String Gender;
        public int Salary;

        public PayrollService(int id, String name, Date startDate, String gender, int salary) {
            this.id = id;
            Name = name;
            StartDate = startDate;
            Gender = gender;
            Salary = salary;
        }
    }
