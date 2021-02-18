package com.techproed;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import org.junit.Test;

import static io.restassured.RestAssured.*;
public class GetRequest05Tekrar {
     /*
     When I send a GET request to REST API URL
          https://restful-booker.herokuapp.com/booking/5
     Then HTTP Status Code 200 olsun
     And Response content type “application/JSON” olsun
     And "firstname" "Eric" olsun
     And "totalprice" 138 olsun
     And "checkin" "2017-05-23" olsun
 */
    @Test
    public void get01(){
        //1-url gitme
        String url="https://restful-booker.herokuapp.com/booking/5";

        //3=request olusturmak
        Response response = given().
                when().
                get(url);
        response.prettyPrint();
        //assert
        response.
                then().
                assertThat().
                contentType(ContentType.JSON).
                body("firstname", equalTo("Sally"),
                "totalprice",equalTo(349),"bookingdates.checkin",equalTo("2017-12-09"));
    }
}
