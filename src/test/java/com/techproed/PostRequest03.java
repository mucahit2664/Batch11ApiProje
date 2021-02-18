package com.techproed;
import static org.hamcrest.Matchers.*;
import TestBase.TestBaseJsonPlaceHolder;
import TestData.JsonPlaceHolderTestData;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PostRequest03 extends TestBaseJsonPlaceHolder {
    /*
	 	When
	  		I send POST Request to the Url https://jsonplaceholder.typicode.com/todos
	  		with the request body {
								    "userId": 55,
								    "title": "Tidy your room",
								    "completed": false
								   }
		Then
			Status code is 200
			And response body is like {
									    "userId": 55,
									    "title": "Tidy your room",
									    "completed": false,
									    "id": 201
									   }
	 */

    @Test
    public void post01(){
        // Url
        spec01.pathParam("todos","todos");
        // ReqBody i olustur
        JsonPlaceHolderTestData expectedObj = new JsonPlaceHolderTestData();
        JSONObject exptectedDataJSON = expectedObj.setUpPostRequestByJSONObject();

        // Request i gonder
        Response response = given().contentType(ContentType.JSON).
                spec(spec01).
                auth().basic("admin","password123").
                body(exptectedDataJSON.toString()).
                when().
                post("/{todos}");
        response.prettyPrint();
// Assertion
        // Homework
        // 1. Body i kullnarak-- Matchers Body + JSONObject
        response.then().
                assertThat().
                statusCode(201).
                body("userId",equalTo(exptectedDataJSON.getInt("userId")),
                        "title",equalTo(exptectedDataJSON.getString("title")),
                        "completed",equalTo(exptectedDataJSON.getBoolean("completed")));
        // HardAssert JSONObject + JsonPath
        JsonPath json = response.jsonPath();
        Assert.assertEquals(expectedObj.statusCode,response.getStatusCode());
        Assert.assertEquals(exptectedDataJSON.getBoolean("completed"),json.getBoolean("completed"));
        Assert.assertEquals(exptectedDataJSON.getString("title"),json.getString("title"));
        Assert.assertEquals(exptectedDataJSON.getInt("userId"),json.getInt("userId"));

// SoftAssert JSONObject + GSOn- DE-Serialization
        Map<String,Object> actualdDataGsonMap=response.as(HashMap.class);
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(actualdDataGsonMap.get("completed"),exptectedDataJSON.get("completed"));
        softAssert.assertEquals(actualdDataGsonMap.get("title"),exptectedDataJSON.get("title"));
        softAssert.assertEquals(actualdDataGsonMap.get("userId"),exptectedDataJSON.get("userId"));
        softAssert.assertAll();
}



}
