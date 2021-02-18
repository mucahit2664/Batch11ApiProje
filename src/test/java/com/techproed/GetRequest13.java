package com.techproed;

import TestBase.TestBaseHerOkuApp;
import TestData.HerOkuAppTestData;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.Assert;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GetRequest13 extends TestBaseHerOkuApp {
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
        //1. adım Url
        spec02.pathParam("bookingId",1);
        // 2 Expected (Beklenen) datayı olustur.
        HerOkuAppTestData expectedObj = new HerOkuAppTestData();

        Map<String,Object> expecTedDataMap = expectedObj.setUpData();
        System.out.println(expecTedDataMap);


        // Request gonder
        Response response = given().
                spec(spec02).
                when().
                get("/{bookingId}");
        response.prettyPrint();

        //4. assertion
        Map<String,Object> actualDataMap = response.as(HashMap.class);
        System.out.println(actualDataMap);

        Assert.assertEquals(expecTedDataMap.get("firstname"),actualDataMap.get("firstname"));
        Assert.assertEquals(expecTedDataMap.get("lastname"),actualDataMap.get("lastname"));
        Assert.assertEquals(expecTedDataMap.get("totalprice"),actualDataMap.get("totalprice"));
        Assert.assertEquals(expecTedDataMap.get("depositpaid"),actualDataMap.get("depositpaid"));
        Assert.assertEquals(((Map)expecTedDataMap.get("bookingDates")).get("checkin"),(((Map)actualDataMap.get("bookingdates"))
                .get("checkin")));
        Assert.assertEquals(((Map)expecTedDataMap.get("bookingDates")).get("checkout"),(((Map)actualDataMap.get("bookingdates"))
                .get("checkout")));


    }
}
