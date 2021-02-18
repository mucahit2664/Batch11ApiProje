package com.techproed;

import TestBase.TestBaseHerOkuApp;
import TestData.HerOkuAppTestData;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.Assert;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
public class GetRequest13Tekrar extends TestBaseHerOkuApp {
     /*
          When
              I send GET Request to https://restful-booker.herokuapp.com/booking/1
          Then
              Response body should be like that;
              {
                 "firstname": "Eric",
                 "lastname": "Smith",
                 "totalprice": 555,
                 "depositpaid": false,
                 "bookingdates": {
                     "checkin": "2016-09-09",
                     "checkout": "2017-09-21"
                  }
             }
     */
    @Test
    public void get01(){
        //1-url ol
        spec02.pathParam("bookingId",1);

    }
}
