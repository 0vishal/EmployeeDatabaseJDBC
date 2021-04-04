package jdbc;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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



    }
