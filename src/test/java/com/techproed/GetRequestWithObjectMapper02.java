package com.techproed;

import TestBase.TestBaseHerOkuApp;
import Utilities.JsonUtil;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class GetRequestWithObjectMapper02 extends TestBaseHerOkuApp {
    /*
		 	When
		 		I send GET Request to the URL https://restful-booker.herokuapp.com/booking/2
		 	Then
		 		Status code is 200
		 		And response body is like {
										    "firstname": "Mark",
										    "lastname": "Ericsson",
										    "totalprice": 726,
										    "depositpaid": true,
										    "bookingdates": {
										        "checkin": "2015-08-07",
										        "checkout": "2020-10-25"
										     }
										  }
		 */
    @Test
    public void get01(){
        // Url i olustur
        spec02.pathParam("id",2);
        // Expected Data yi olustur
        String expectedJson = "{\n" +
                "\"firstname\": \"Sally\",\n" +
                "\"lastname\": \"Brown\",\n" +
                " \"totalprice\": 711,\n" +
                "\"depositpaid\": false,\n" +
                "\"bookingdates\": {\n" +
                "\"checkin\": \"2019-05-15\",\n" +
                "\"checkout\": \"2020-07-27\"\n" +
                "}\n" +
                " }";
        Map<String,Object> expectedDataMap = JsonUtil.convertJsonToJava(expectedJson,Map.class);
        System.out.println(expectedDataMap);

        // Request i gonder
        Response response = given().
                spec(spec02).
                when().
                get("/{id}");
        response.prettyPrint();

        Map<String,Object> actualDataMap = JsonUtil.convertJsonToJava(response.asString(),Map.class);
        System.out.println(actualDataMap);

        response.
                then().
                assertThat().
                statusCode(200);

        // Assertion
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualDataMap.get("firstname"),expectedDataMap.get("firstname"));
        softAssert.assertEquals(actualDataMap.get("lastname"),expectedDataMap.get("lastname"));
        softAssert.assertEquals(actualDataMap.get("totalprice"),expectedDataMap.get("totalprice"));
        softAssert.assertEquals(actualDataMap.get("depositpaid"),expectedDataMap.get("depositpaid"));
        softAssert.assertEquals(((Map)actualDataMap.get("bookingdates")).get("checkin"),(((Map)expectedDataMap.get("bookingdates")).get("checkin")));
        softAssert.assertEquals(((Map)actualDataMap.get("bookingdates")).get("checkout"),(((Map)expectedDataMap.get("bookingdates")).get("checkout")));

        softAssert.assertAll();


    }
}
