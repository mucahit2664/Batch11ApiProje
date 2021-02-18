package com.techproed;
import TestBase.TestBaseHerOkuApp;
import TestData.HerOkuAppTestData;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.json.JSONObject;
import org.junit.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PostRequest02 extends TestBaseHerOkuApp {
    /*
	 	When
	 		I send POST Request to the Url https://restful-booker.herokuapp.com/booking
	 		with the request body {
								    "firstname": "Selim",
								    "lastname": "Ak",
								    "totalprice": 11111,
								    "depositpaid": true,
								    "bookingdates": {
								        "checkin": "2020-09-09",
								        "checkout": "2020-09-21"
								     }
								  }
	 	Then
	 		Status code is 200
	 		And response body should be like {
											    "bookingid": 11,
											    "booking": {
											        "firstname": "Selim",
											        "lastname": "Ak",
											        "totalprice": 11111,
											        "depositpaid": true,
											        "bookingdates": {
											            "checkin": "2020-09-09",
											            "checkout": "2020-09-21"
											        }
											    }
											 }
	 */
    @Test
    public void post01(){
        // 1. Url i olustur
// Beklenen expected Data yı olustur
        HerOkuAppTestData expectedObj = new HerOkuAppTestData();
        JSONObject expectedBodyData = expectedObj.setUpDataJSONObject();
        System.out.println(expectedBodyData);
        // Request gonder
        Response response = given().
                contentType(ContentType.JSON).
                spec(spec02).
                auth().
                basic("admin","password123").
                //JSonObject ile body gonderirken toString kullanmak gerekir.
                        body(expectedBodyData.toString()).
                        when().
                        post();
        response.prettyPrint();
        // 4. Assertion
        // 1. yol JSonObject ve body
        response.then().
                assertThat().
                statusCode(200).
                body("booking.firstname",equalTo(expectedBodyData.getString("firstname")),
                        "booking.lastname",equalTo(expectedBodyData.getString("lastname")),
                        "booking.totalprice",equalTo(expectedBodyData.getInt("totalprice")),
                        "booking.depositpaid",equalTo(expectedBodyData.getBoolean("depositpaid")),
                        "booking.bookingdates.checkin",equalTo(expectedBodyData.getJSONObject("bookingdates").getString("checkin")),
                        "booking.bookingdates.checkout",equalTo(expectedBodyData.getJSONObject("bookingdates").getString("checkout")));
        // 2.yol
        // HardAssert JsonObject e jsonPath
        JsonPath json = response.jsonPath();
        Assert.assertEquals(expectedBodyData.getString("firstname"),json.getString("booking.firstname"));
        Assert.assertEquals(expectedBodyData.getString("lastname"),json.getString("booking.lastname"));
        Assert.assertEquals(expectedBodyData.getInt("totalprice"),json.getInt("booking.totalprice"));
        Assert.assertEquals(expectedBodyData.getBoolean("depositpaid"),json.getBoolean("booking.depositpaid"));
        Assert.assertEquals(expectedBodyData.getJSONObject("bookingdates").getString("checkin"),json.getString("booking.bookingdates.checkin"));
        Assert.assertEquals(expectedBodyData.getJSONObject("bookingdates").getString("checkout"),json.getString("booking.bookingdates.checkout"));

        // 3. yol Soft Assertion ve JSONObject ve GSON  ---> DE-SErialization ile

        Map<String,Object> actualDataMap = response.as(HashMap.class);
        System.out.println(actualDataMap);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(((Map)actualDataMap.get("booking")).get("firstname"),expectedBodyData.getString("firstname"));
        softAssert.assertEquals(((Map) actualDataMap.get("booking")).get("lastname"),expectedBodyData.getString("lastname"));
        softAssert.assertEquals(((Map) actualDataMap.get("booking")).get("totalprice"),expectedBodyData.getInt("totalprice"));
        softAssert.assertEquals(((Map) actualDataMap.get("booking")).get("depositpaid"),expectedBodyData.getBoolean("depositpaid"));
        softAssert.assertEquals(((Map)((Map)actualDataMap.get("booking")).get("bookingdates")).get("checkin"),expectedBodyData.getJSONObject("bookingdates").getString("checkin"));
        softAssert.assertEquals(((Map)((Map)actualDataMap.get("booking")).get("bookingdates")).get("checkout"),expectedBodyData.getJSONObject("bookingdates").getString("checkout"));


        softAssert.assertAll();


    }
}