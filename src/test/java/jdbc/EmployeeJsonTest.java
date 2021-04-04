package jdbc;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class EmployeeJsonTest {


        @BeforeAll
        static void setup(){
            RestAssured.baseURI="http://localhost";
            RestAssured.port=4000;
        }

        public EmployeeDataJson[] getEmployeelist(){
            Response response= RestAssured.get("/employees");
            System.out.println("employee entries in json server: \n"+response.asString());
            EmployeeDataJson[] jsonServerEmployeeData=new Gson().fromJson(response.asString(),EmployeeDataJson[].class);
            return jsonServerEmployeeData;
        }

        public Response addEmployeeDataToJsonServer(EmployeeDataJson restAssureEmpData){
            String employee=new Gson().toJson(restAssureEmpData);
            RequestSpecification requestSpecification=RestAssured.given();
            requestSpecification.header("Content-Type","application/json");
            requestSpecification.body(employee);
            return requestSpecification.post("/employees");
        }

        @Test
        public void RetrieveServerData(){
            EmployeeDataJson[] restAssureEmployeeData=getEmployeelist();
            System.out.println(restAssureEmployeeData);
            Assertions.assertEquals(3,restAssureEmployeeData.length);
        }

        @Test
        public void addMultipleEmployee(){
            EmployeeDataJson[] restAssureEmployeeData=getEmployeelist();
            EmployeeDataJson jsonServerEmployeeData1=new EmployeeDataJson(21,"Ram",40000);
            EmployeeDataJson jsonServerEmployeeData2=new EmployeeDataJson(31,"Sham",40000);
            Response response1=addEmployeeDataToJsonServer(jsonServerEmployeeData1);
            Response response2=addEmployeeDataToJsonServer(jsonServerEmployeeData2);
            int statusCode1= response1.statusCode();
            int statusCode2= response2.statusCode();
            Assertions.assertEquals(201,statusCode1);
            Assertions.assertEquals(201,statusCode2);
            Assertions.assertEquals(7,restAssureEmployeeData.length);
        }

        @Test
        public void Updatesalary() throws SQLException {
            EmployeeDataJson[] restAssureEmployeeData=getEmployeelist();
            String empJson=new Gson().toJson(restAssureEmployeeData);
            Assertions.assertEquals(9,restAssureEmployeeData.length);
            RequestSpecification requestSpecification=RestAssured.given();
            requestSpecification.header("Content-Type","application/json");
            requestSpecification.body("{\"name\":\"shali\",\"salary\":\"15000\"}");
            Response response=requestSpecification.put("/employees/update/2");

            int statusCode=response.getStatusCode();
            Assertions.assertEquals(200,statusCode);
        }




    }
