package com.techproed;

import TestBase.TestBaseJsonPlaceHolder;
import TestData.JsonPlaceHolderTestData;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class PutRequest01 extends TestBaseJsonPlaceHolder {
    /*
	 	When
	 		I send PUT Requst to the Url https://jsonplaceholder.typicode.com/todos/198
	 		with the PUT Request body like {
										    "userId": 21,
										    "title": "Wash the dishes",
										    "completed": false
										   }
	   Then
	   	   Status code is 200
	   	   And response body is like  {
									    "userId": 21,
									    "title": "Wash the dishes",
									    "completed": false,
									    "id": 198
									  }
	 */
    @Test
    public void put01(){
        spec01.pathParams("todosPath","todos","id",198);
        // reg boody olusturmak icin
        JsonPlaceHolderTestData expectedObj=new JsonPlaceHolderTestData();
        JSONObject expectedDataJSON=expectedObj.setUpPutRequestByJSONObject();
        Response response=given().
                contentType(ContentType.JSON).
                spec(spec01).body(expectedDataJSON.toString()).
                when().
                put("/{todosPath}/{id}");
        response.prettyPrint();
        // Assertion
        // 1. yol odev--- body ve JsonObject
        // 2. yol odev--- jsonPath ve JsonObject
        // 3. yol Gson ve JsonObject
        response.
                then().
                assertThat().
                statusCode(200).
                body("completed", equalTo(expectedDataJSON.getBoolean("completed")),
                        "title", equalTo(expectedDataJSON.getString("title")),
                        "userId", equalTo(expectedDataJSON.getInt("userId")));
        // 2. yoll Assertler Hart
        JsonPath json=response.jsonPath();
        Assert.assertEquals(expectedDataJSON.getBoolean("completed"),json.getBoolean("completed"));
        Assert.assertEquals(expectedDataJSON.getString("title"),json.getString("title"));
        Assert.assertEquals(expectedDataJSON.getInt("userId"),json.getInt("userId"));
        //3. yol DE--Serializasion
        HashMap<String,Object> actualDataMap=response.as(HashMap.class);
        Assert.assertEquals(expectedDataJSON.getBoolean("completed"),actualDataMap.get("completed"));
        Assert.assertEquals(expectedDataJSON.getString("title"),actualDataMap.get("title"));
        Assert.assertEquals(expectedDataJSON.getInt("userId"),actualDataMap.get("userId"));

    }
}
