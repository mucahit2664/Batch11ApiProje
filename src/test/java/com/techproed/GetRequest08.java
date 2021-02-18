package com.techproed;

import TestBase.TestBaseDummy;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;

public class GetRequest08 extends TestBaseDummy {
    	/*
    	Url: "http://dummy.restapiexample.com/api/v1/employees
    	1) Butun calisanlarin isimlerini consola yazdır
    	2) 3. calisan kisinin ismini konsola yazdır
    	3) Ilk 5 calisanin adini konsola yazdir
    	4) En son calisanin adini konsola yazdir.

	 */

    @Test
    public void get01(){
        // Url olusturmak
        spec03.pathParam("employees","employees");
        // Request olusturmak

        Response response = given().
                spec(spec03).
                when().
                get("/{employees}");
        response.prettyPrint();

        JsonPath json = response.jsonPath();
        System.out.println(json.getString("data.employee_name"));
        System.out.println(json.getString("data.employee_salary"));
        System.out.println(json.getString("data.employee_name[2]"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(json.getString("data.employee_name[2]"),"Ashton Cox");
        System.out.println(json.getString("data.employee_name[0,1,2,3,4]"));
        System.out.println(json.getString("data[0,1,2,3,4].employee_name"));
        System.out.println(json.getString("data[-1].employee_name"));
        System.out.println(json.getString("data.employee_name[-1]"));
        softAssert.assertAll();



    }
}
