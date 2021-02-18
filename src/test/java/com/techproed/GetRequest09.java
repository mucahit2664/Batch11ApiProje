package com.techproed;

import TestBase.TestBaseDummy;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GetRequest09 extends TestBaseDummy {

    /*
                     Warm Up (10 Minutes)
     1)Create a class and name it as GetRequest09
     2)When
          I send a GET Request to http://dummy.restapiexample.com/api/v1/employees
     Then
         status code is 200
         5. calisanin isminin "Airi Satou" oldugunu
         And the name of the 5th employee is "Airi Satou"
         And the salary of the 6th employee is "372000"
         6. calisanin maasinin 372000 oldugunu
         And there are "24" employees
         24 calisan oldugunu
         And "Rhona Davidson" is one of the employees
         "Rhona Davidson" nun calisanlardan birisi oldugunu
         And "21", "23", "61" are among the employee ages
         Calisan yaslarinda "21","23" ve "61" sayilari oldugunu
         hardAssert ile assert et.
     3)Do the assertions by using Hard Assertion
*/
    @Test
    //1. adim URL i olusturmak
    public void get01(){
        spec03.pathParam("employeePath","employees");

        // 2. adim test datalarini olusturmak

        // 3. adim Request olusturma
        Response response = given().
                spec(spec03).
                when().
                get("/{employeePath}");
        //   response.prettyPrint();
        // 1. yol EqualTo-- Body ile assert ediyoruz
        response.
                then().
                assertThat().
                statusCode(200).
                body("data.employee_name[4]",equalTo("Airi Satou"),
                        "data[5].employee_salary",equalTo("372000"),
                        "data.id",hasSize(24),
                        "data.employee_name",hasItem("Rhona Davidson"),
                        "data.employee_age",hasItems("21", "23", "61"));

        // 2. yol Hard Assertion
        // JsonPath ile
        JsonPath json = response.jsonPath();
        Assert.assertEquals("Airi Satou",json.getString("data.employee_name[4]"));
        Assert.assertEquals("372000",json.getString("data[5].employee_salary"));
        //  System.out.println(json.getList("data.id"));
        Assert.assertEquals(24,json.getList("data.id").size());
        // System.out.println(json.getList("data.employee_name"));
        Assert.assertTrue(json.getList("data.employee_name").contains("Rhona Davidson"));

        List<String> yasListesi = new ArrayList<>();
        yasListesi.add("21");
        yasListesi.add("23");
        yasListesi.add("61");
        Assert.assertTrue(json.getList("data.employee_age").containsAll(yasListesi));

    }

}