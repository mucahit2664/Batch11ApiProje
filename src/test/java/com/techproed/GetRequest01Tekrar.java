package com.techproed;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class GetRequest01Tekrar {
         /*
       Given: Baslangıc icin gereklilikleri ifade eder.
       When: Kullanicinin aksiyonunu ifade eder.
       Then: Ciktilari ifade eder-- Assert ler genelde burada yapilir
       And: Herhangi iki coklu adim arasinda kullanilabilir.
        Positive Scenario
     When I send a GET Request to the URL https://restful-booker.herokuapp.com/booking/3
     Then
     HTTP Status code should be 200
     And  Content type should be Json
     And  Status Line should be HTTP/1.1 200 OK
    */
@Test
    public void get01(){
    //1-url olsuturmak
    String url=" https://restful-booker.herokuapp.com/booking/3";
    //2=Request gondermel
   Response response = given().
            accept("application/json").// accept type ın Json formatında olmasını istiyorum
            when().
            get(url);
    response.prettyPrint();
    //ASSERT yapalim
    response.
            then().
            assertThat().
            contentType(ContentType.JSON).statusLine("HTTP/1.1 200 OK");
    //KONSOLDA STATUS CODE,STATUS LINE BASIKLARI GORELI M
    System.out.println("Status code "+response.getStatusCode());
    System.out.println(response.getStatusLine());
    System.out.println(response.getHeaders());

    }
}
