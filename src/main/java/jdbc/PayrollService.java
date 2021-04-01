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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = Date.valueOf(startDate);
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    @Override
    public String toString() {
        return "PayrollService{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Salary=" + Salary +
                '}';
    }
}