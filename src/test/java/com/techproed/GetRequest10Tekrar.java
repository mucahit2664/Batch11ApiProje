package com.techproed;

import TestBase.TestBaseDummy;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GetRequest10Tekrar extends TestBaseDummy {
     /*
	 When
		 I send GET Request to URL http://dummy.restapiexample.com/api/v1/employees
	 Then
		 Status code is 200
		 1)Print all ids greater than 10 on the console
		 // 10 dan büyük olan id leri yazdir
		   Assert that there are 14 ids greater than 10
		   10 dan 14 buyuk olan 14 id oldugunu assert et
		 2)Print all ages less than 30 on the console
		 30 dan kucuk tum yaslari yazdır
		   Assert that maximum age is 23
		   30 dan kucuk en buyuk yasın 23 oldugunu assert et
		 3)Print all employee names whose salaries are greater than 350,000
		 Maası 350 binden buyuk olanları yazdır, Charde Marshall onlrdan birisi mi assert et.
		   Assert that Charde Marshall is one of the employees whose salary is greater than 350,000
	 */
    @Test
    public void get01(){
        //1-url olusuturduk
        spec03.pathParam("employeePath","employees");
        //2-
        //3-request olsuturmak
        Response response=given().
                             spec(spec03).
                                       when().
                                          get("/{employeePath}");

      //  response.prettyPrint();
        response.
                then().
                assertThat().
                statusCode(200);

        JsonPath json=response.jsonPath();
        // 10 dan büyük olan id leri yazdir
        List<String>listId=json.getList("data.findAll{Integer.valueOf(it.id)>10}.id");
        System.out.println(listId);
        Assert.assertEquals(14,listId.size());
        //30 dan kucuk tum yaslari yazdır
        List<String>listAge=json.getList("data.findAll{Integer.valueOf(it.employee_age)<30}.employee_age");
        System.out.println(listAge);
        //   30 dan kucuk en buyuk yasın 23 oldugunu assert et
        List<Integer>listAgeInt=new ArrayList<>();
        for (String w:listAge){
            listAgeInt.add(Integer.valueOf(w));
        }
        System.out.println(listAgeInt);
        Collections.sort(listAgeInt);
        System.out.println(listAgeInt);
        Assert.assertTrue(23==listAgeInt.get(listAgeInt.size()-1));
      //  Maası 350 binden buyuk olanları yazdır, Charde Marshall onlrdan birisi mi assert et.
        List<String>nameLIST= json.getList("data.findAll{Integer.valueOf(it.employee_salary)>350000}.employee_name");
        System.out.println(nameLIST);
        Assert.assertTrue(nameLIST.contains("Charde Marshall"));
    }


        }

