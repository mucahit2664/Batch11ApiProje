package com.techproed;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


public class GetRequest03 {
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

        String url = "https://restful-booker.herokuapp.com/booking/7";

        Response response = given().
                accept("application/json").
                when().
                get(url);
        response.prettyPrint();

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON); // contentType("application/json")
        // 1. yol
     /*   response.then().
                assertThat().
                body("firstname", Matchers.equalTo("Jim")).
                body("lastname",Matchers.equalTo("Brown")).
                body("totalprice",Matchers.equalTo(134)).
                body("depositpaid",Matchers.equalTo(true)).
                body("bookingdates.checkin",Matchers.equalTo("2021-01-25")).
                body("bookingdates.checkout",Matchers.equalTo("2021-01-26"));
            //    body("additionalneeds",Matchers.equalTo("Breakfast")); */

        // 2. yol
        // Tekrarlı body lerden kurtulmak istiyorum.

        response.
                then().
                assertThat().
                body("firstname",equalTo("Jim"),
                        "lastname",equalTo("Jones"),
                        "totalprice",equalTo(932),
                        "depositpaid",equalTo(false),
                        "bookingdates.checkin",equalTo("2018-02-17"),
                        "bookingdates.checkout",equalTo("2020-09-24"));
    }






        }