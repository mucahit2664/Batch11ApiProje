package com.techproed;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GetRequest05 {
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
        String url = "https://restful-booker.herokuapp.com/booking/5";

        Response response = given().
                when().
                get(url);

        response.prettyPrint();

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("firstname", equalTo("Mark"),
                        "totalprice",equalTo(821),
                        "bookingdates.checkin",equalTo("2015-05-10"));


    }
}




