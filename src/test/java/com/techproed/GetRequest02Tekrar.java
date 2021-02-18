package com.techproed;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class GetRequest02Tekrar {
     /*
	 Positive Scenario:
	 when() Bir GET Request asagida verilen Endpoint'e yollanir
	        https://restful-booker.herokuapp.com/booking
	 and()  Accept Type'i "application/json" dir.
	 then() status code 200'dur
	 and()  content type  "application/json" dir.
	*/
@Test
    public  void get01(){
    String url="https://restful-booker.herokuapp.com/booking";
    Response response=given().
                    accept(ContentType.JSON).
                         when().
                         get(url);
    response.prettyPrint();
    //assert yapalim
    response.
            then().
             assertThat().
            contentType(ContentType.JSON).
            statusCode(200);
    Assert.assertEquals(200,response.getStatusCode());
}
        /*
         Negative Scenario:
         when() Bir GET Request asagida verilen Endpoint'e yollanir
                https://restful-booker.herokuapp.com/booking/1001
         and()  Accept Type'i "application/json" dir.
         then() status code 404'dur.
         */
@Test
    public void get02(){
    String url=" https://restful-booker.herokuapp.com/booking/1001";

    Response response=given().
            accept(ContentType.JSON).
            when().
            get(url);
    response.prettyPrint();
    System.out.println(response.getHeaders());
    //asserrt
    response.
            then().
            assertThat().
            statusCode(404);
    Assert.assertEquals(404,response.getStatusCode());


}
    /*
	 Negative Scenario:
	 when() Bir GET Request asagida verilen Endpoint'e yollanir
	        https://restful-booker.herokuapp.com/booking/1001
	 then() status code 404'dur
	 and()  Response body'de "Not Found" var
	 and()  Response body'de "API" yok
	 */
    @Test
    public void get03(){
        String url="https://restful-booker.herokuapp.com/booking/1001";
        Response response=given().
                accept("application/json").
                when().
                get(url);
        response.prettyPrint();
        //assert
        response.then().assertThat().statusCode(404);
        Assert.assertTrue(response.asString().contains("Not Found"));
        Assert.assertFalse(response.asString().contains("API"));
    }
}
