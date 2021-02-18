package com.techproed;

import TestBase.TestBaseDummy;
import TestData.DummyTestData;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PostRequest01 extends TestBaseDummy {
    /*
	 	 When
	 	  	I send a POST Request to the Url http://dummy.restapiexample.com/api/v1/create
	 	  	by using the following Request Body {
												    "name":"Ahmet Aksoy",
												    "salary":"1000",
												    "age":"18",
												    "profile_image": ""
												}
	 	 Then
	 	  	Status code is 200
	 	  	And response body should be like {
											    "status": "success",
											    "data": {
											        "name": "Ahmet Aksoy",
											        "salary": "1000",
											        "age": "18",
											        "profile_image": null
											    },
											    "message": "Successfully! Record has been added."
										 }
	*/
    /*
    Genel olarak API ler authorization isterler (yetki)--- Post islemi icin
     */

    @Test
    public void post01() {
        // 1. URL i olustur
        spec03.pathParam("createPath", "create");

        // 2. ReqBody olusturucam
        DummyTestData expectedObj = new DummyTestData();
        Map<String, String> reqBodyMap = expectedObj.setUpData03();
        System.out.println(reqBodyMap);

        // Request i gonder
        Response response = given().
                contentType(ContentType.JSON).
                spec(spec03).
                auth().
                basic("admin", "password123").
                body(reqBodyMap).
                when().
                post("/{createPath}");
        response.prettyPrint();

        // 4. Assertion
        // 1. yol jsonPath

        JsonPath json = response.jsonPath();

        Assert.assertEquals(reqBodyMap.get("name"), json.getString("data.name"));
        Assert.assertEquals(reqBodyMap.get("salary"), json.getString("data.salary"));
        Assert.assertEquals(reqBodyMap.get("age"), json.getString("data.age"));

        if (reqBodyMap.get("profile_image").equals("")) {
            reqBodyMap.put("profile_image", null);
        }
        Assert.assertEquals(reqBodyMap.get("profile_image"), json.get("data.profile_image"));

        Map<String, String> expectedMsgMap = expectedObj.setUpMessageData();
        System.out.println(expectedMsgMap);
        Assert.assertEquals(expectedMsgMap.get("message"), json.getString("message"));

        // 2. yol GSon  -- -> De-Serialization

        Map<String, Object> actualDataMap = response.as(HashMap.class);
        System.out.println(actualDataMap);
        Assert.assertEquals(reqBodyMap.get("name"), ((Map) actualDataMap.get("data")).get("name"));
        Assert.assertEquals(reqBodyMap.get("salary"), ((Map) actualDataMap.get("data")).get("salary"));
        Assert.assertEquals(reqBodyMap.get("age"), ((Map) actualDataMap.get("data")).get("age"));
       /*
       if (reqBodyMap.get("profile_image").equals("")){
            reqBodyMap.put("profile_image",null);
        }
        YUkarıda if statement ı kullandıgımız icin tekrara gerel yok
        Cunku aynı scope aynı--- aynı metodtayız--
       */

        Assert.assertEquals(reqBodyMap.get("profile_image"), ((Map) actualDataMap.get("data")).get("profile_image"));
        Assert.assertEquals(expectedMsgMap.get("message"), actualDataMap.get("message"));
        Assert.assertEquals(expectedMsgMap.get("status"), actualDataMap.get("status"));

        // 3. yol JSonObject + jsonPath---- softAssert
        SoftAssert softAssert = new SoftAssert();
        JSONObject expectedDataJsonObject = expectedObj.setUpPostReqBodyByUsingJSONObject();
        System.out.println(expectedDataJsonObject);
        softAssert.assertEquals(json.getString("data.name"), expectedDataJsonObject.getString("name"));
        softAssert.assertEquals(json.getString("data.salary"), expectedDataJsonObject.getString("salary"));
        softAssert.assertEquals(json.getString("data.age"), expectedDataJsonObject.getString("age"));

        // softAssert.assertEquals(json.get("data.profile_image"),expectedDataJsonObject.getString("profile_image"));

        JSONObject msjJSONObject = expectedObj.setUpMessageDataByUsingJSONObject();
        System.out.println(msjJSONObject);
        softAssert.assertEquals(json.getString("message"), msjJSONObject.getString("message"));
        softAssert.assertEquals(json.getString("status"), msjJSONObject.getString("status"));
        softAssert.assertAll();
    }
}