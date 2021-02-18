package com.techproed;
import TestBase.TestBaseJsonPlaceHolder;

import Utilities.JsonUtil;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;


public class GetRequesTWithObjectMapper01 extends TestBaseJsonPlaceHolder {
    /*
	 	When
	 		I send GET Request to the URL https://jsonplaceholder.typicode.com/todos/198
	 	Then
	 		Status code is 200
	 		And response body is like {
									    "userId": 10,
									    "id": 198,
									    "title": "quis eius est sint explicabo",
									    "completed": true
									  }
     */


   /*
	 	When
	 		I send GET Request to the URL https://jsonplaceholder.typicode.com/todos/198
​
	 	Then
	 		Status code is 200
	 		And response body is like {
									    "userId": 10,
									    "id": 198,
									    "title": "quis eius est sint explicabo",
									    "completed": true
									  }

     */

    @Test

    public void get01(){
        // Url i olustur
        spec01.pathParams("todosPath","todos",
                "id",198);
        // Expected- Beklenen Data yı olustur
        String expectedJson = "{\n" +
                " \"userId\": 10,\n" +
                "\"id\": 198,\n" +
                "\"title\": \"quis eius est sint explicabo\",\n" +
                " \"completed\": true\n" +
                " }";

        Map<String,Object> expectedMap = JsonUtil.convertJsonToJava(expectedJson, Map.class);
        System.out.println(expectedMap);

        // Request i gonder
        Response response = given().
                spec(spec01).
                when().
                get("/{todosPath}/{id}");
        response.prettyPrint();

        // Response body i Map e ceviricem..

        Map<String,Object> actualDataMap = JsonUtil.convertJsonToJava(response.asString(),Map.class);
        System.out.println(actualDataMap);

        // Assertion
        Assert.assertEquals(expectedMap.get("userId"),actualDataMap.get("userId"));
        Assert.assertEquals(expectedMap.get("completed"),actualDataMap.get("completed"));
        Assert.assertEquals(expectedMap.get("title"),actualDataMap.get("title"));
        Assert.assertEquals(expectedMap.get("id"),actualDataMap.get("id"));


    }






}
