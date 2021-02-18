package com.techproed;

import TestBase.TestBaseDummy;
import TestData.DummyTestData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static io.restassured.RestAssured.*;

public class GetRequest14Tekrar extends TestBaseDummy {
     /*
       When
           I send a request to http://dummy.restapiexample.com/api/v1/employees
       Then
           Status code is 200
           And the highest salary is 725000
           And the minimum age is 19
           And the second lowest salary is 675000
   */
    @Test
    public void get01(){
        //Url olustur
        spec03.pathParam("employeesPath","employees");
        //Beklenen datayi olustur
        DummyTestData expectedObj=new DummyTestData();
        Map<String,Integer> expectedDataMap=expectedObj.setUpData2();
        System.out.println(expectedDataMap);
        //3.Request olustur
        Response response=given().
                spec(spec03).when().get("/{employeesPath}");
        // response.prettyPrint();
        //4.Assert         //1.yol JsonPath
        JsonPath json=response.jsonPath();
        List<String> employeesSalary=json.get("data.employee_salary");
        List<Integer> employeesSalaryList=new ArrayList<>();
        for (String  w: employeesSalary
        ) {
            employeesSalaryList.add(Integer.valueOf(w));
        }
        Collections.sort(employeesSalaryList);
        System.out.println(employeesSalaryList);
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals((Integer)response.getStatusCode(),expectedDataMap.get("Status code"));
        softAssert.assertEquals(employeesSalaryList.get(employeesSalaryList.size()-1),expectedDataMap.get("EnYuksekMaas"));
        softAssert.assertEquals(employeesSalaryList.get(employeesSalaryList.size()-2),expectedDataMap.get("IkinciYuksekMaas"));
        List<String> employeesAge=json.get("data.employee_age");
        List<Integer> employeesAgeList=new ArrayList<>();
        for (String age: employeesAge
        ) {
            employeesAgeList.add(Integer.valueOf(age));
        }
        softAssert.assertEquals(Collections.min(employeesAgeList),expectedDataMap.get("EnKucukYas"));

        //2.Yol DE-Serialization
        Map<String,Object> actualDataMap=response.as(HashMap.class);
        System.out.println(actualDataMap);
        softAssert.assertEquals((Integer) response.getStatusCode(),expectedDataMap.get("Status code"));
        int employeesSize=((List)actualDataMap.get("data")).size();
        System.out.println(employeesSize);
        List<Integer> employeesSalary2=new ArrayList<>();
        for (int i = 0; i <employeesSize ; i++) {
            employeesSalary2.add(Integer.valueOf((String)(((Map)((List)actualDataMap.get("data")).get(i)).get("employee_salary"))));
        }
        System.out.println("actuel salary" +employeesSalary2);
        Collections.sort(employeesSalary2);
        softAssert.assertEquals(employeesSalary2.get(employeesSalary2.size()-1),expectedDataMap.get("EnYuksekMaas"));
        softAssert.assertEquals(employeesSalary2.get(employeesSalary2.size()-2),expectedDataMap.get("IkinciYuksekMaas"));
        List<Integer> employeesAge2=new ArrayList<>();
        for (int i = 0; i <employeesSize ; i++) {
            employeesAge2.add(Integer.valueOf((String)(((Map)(((List) actualDataMap.get("data")).get(i))).get("employee_age"))));
        }
        Collections.sort(employeesAge2);
        softAssert.assertEquals(employeesAge2.get(0),expectedDataMap.get("EnKucukYas"));
        softAssert.assertAll();

    }
}
