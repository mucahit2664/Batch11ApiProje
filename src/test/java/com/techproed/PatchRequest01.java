package com.techproed;

import TestBase.TestBaseJsonPlaceHolder;
import TestData.JsonPlaceHolderTestData;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PatchRequest01 extends TestBaseJsonPlaceHolder {
    /*
	   When
	 		I send PATCH Request to the Url https://jsonplaceholder.typicode.com/todos/198
	 		with the PUT Request body like {
										    "title": "Tidy your room",
										   }
	   Then
	   	   Status code is 200
	   	   And response body is like  {
									    "userId": 10,
									    "title": "Tidy your room",
									    "completed": true,
									    "id": 198
									  }

     */
    @Test
    public void patch01(){
        // Url olustur
        spec01.pathParams("todosPath","todos",
                "id",198);
        // ReqBody olustur
        JsonPlaceHolderTestData expectedObj = new JsonPlaceHolderTestData();
        Map<String,Object> expectedDataMap = expectedObj.setUpPatchDataByUsingMap();
        System.out.println(expectedDataMap);
        // Request i gonder
        Response response = given().
                contentType(ContentType.JSON).
                spec(spec01).
                body(expectedDataMap).
                when().
                patch("/{todosPath}/{id}");
        response.prettyPrint();
        // Assertion
        // 1. yol Body- expectedDataMap
        response.
                then().
                statusCode(200).
                assertThat().
                body("userId", equalTo(expectedDataMap.get("userId")),"completed",
                equalTo(expectedDataMap.get("completed")),"title",equalTo(expectedDataMap.get("title")));
        // 2. yol Jsonpath ile expectedDataMap
        JsonPath json = response.jsonPath();
        Assert.assertEquals(expectedObj.statusCode,response.getStatusCode());
        Assert.assertEquals(expectedObj.userId,json.getInt("userId"));
        Assert.assertEquals(expectedObj.completed,json.getBoolean("completed"));
        Assert.assertEquals(expectedDataMap.get("title"),json.getString("title"));
        // 3. yol Gson ile expectedDataMap
        Map<String,Object> actualdDataGsonMap=response.as(HashMap.class);
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(),expectedObj.statusCode);
        softAssert.assertEquals(expectedObj.completed,expectedDataMap.get("completed"));
        softAssert.assertEquals(actualdDataGsonMap.get("title"),expectedDataMap.get("title"));
        softAssert.assertEquals(expectedObj.userId,expectedDataMap.get("userId"));
    }


}