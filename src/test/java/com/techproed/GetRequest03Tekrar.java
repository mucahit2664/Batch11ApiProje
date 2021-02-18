package com.techproed;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static  org.hamcrest.Matchers.* ;
import org.junit.Test;

import static io.restassured.RestAssured.*;


public class GetRequest03Tekrar {
    /*
	 Positive Scenario:
	 When Asagidaki Endpoint'e bir GET request yolladim
	 https://restful-booker.herokuapp.com/booking/7
     And Accept type “application/json” dir
     Then
     HTTP Status Code 200
     And Response format "application/json"
     And firstname "Eric"
     And lastname "Brown"
     And checkin date "2016-05-20"
     And checkout date "2020-12-18"
	*/
    @Test
    public void get01(){
        String url="https://restful-booker.herokuapp.com/booking/7";
        //
        Response response=given().
                accept("application/json").
                                   when().
                                      get(url);
        response.prettyPrint();
        //assert
        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);
        response.then().assertThat().
                body("firstname",equalTo("Jim"),
                    "lastname",equalTo("Brown"),
               "totalprice",equalTo(922),
                "depositpaid",equalTo(false),
                "bookingdates.checkin",equalTo("2016-11-15"),
               "bookingdates.checkout",equalTo("2018-03-21"));

    }
}
