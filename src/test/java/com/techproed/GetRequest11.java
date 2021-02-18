package com.techproed;

import TestBase.TestBaseJsonPlaceHolder;
import TestData.JsonPlaceHolderTestData;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetRequest11 extends TestBaseJsonPlaceHolder {
    /* When
     I send GET Request to jsonplaceholder api/todos/2
     Status code: 200
             "completed": is false
             "title  is   "quis ut nam facilis et officia qui"
             "userId"  1
     header "Via"   "1.1 vegur"
     header "Server"  "cloudflare"
     De-Serialization: JSON datasını Java Objelerine (Map,List,List of Map, Set) cevirme islemidir.
     GSON dependency sini kullanarak De-SErialization ve Serialization yapılabilir.
     ,
          */
    @Test
    public void get01() {
        // Url i olustur.
        spec01.pathParams("todos", "todos", "id", 198);
        // 2. Expected Datayı (test data) olustur.

        //3. REquest olustur
        Response response = given().
                spec(spec01).
                when().
                get("/{todos}/{id}");
        //  response.prettyPrint();
        JsonPlaceHolderTestData expectedObj = new JsonPlaceHolderTestData();
        HashMap<String, Object> expectedDataMap = expectedObj.setUpData();

        // 1. yol body kullanarak assert edelim
        // body("completed",equealTo(expectedDataMap.get("completed"))
        response.
                then().
                assertThat().
                statusCode((Integer) expectedDataMap.get("Status Code")).
                body("completed", equalTo(expectedDataMap.get("completed")),
                        "title", equalTo(expectedDataMap.get("title")),
                        "userId", equalTo(expectedDataMap.get("userId")))
                .headers("Via", expectedDataMap.get("Via"), "Server", expectedDataMap.get("Server"));


        // De-Serialization ile ??
        // 2. way
        // response u HashMap'e donusturduk
        HashMap<String, Object> actualDataMap = response.as(HashMap.class);
        System.out.println(actualDataMap);
        Assert.assertEquals(expectedDataMap.get("Status Code"), response.getStatusCode());
        Assert.assertEquals(expectedDataMap.get("userId"), actualDataMap.get("userId"));
        Assert.assertEquals(expectedDataMap.get("completed"), actualDataMap.get("completed"));
        Assert.assertEquals(expectedDataMap.get("title"), actualDataMap.get("title"));
        Assert.assertEquals(expectedDataMap.get("Via"), response.getHeader("Via"));
        Assert.assertEquals(expectedDataMap.get("Server"), response.getHeader("Server"));

        // 3.yol softAssert
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), expectedDataMap.get("Status Code"));
        softAssert.assertEquals(actualDataMap.get("userId"), expectedDataMap.get("userId"));
        softAssert.assertEquals(actualDataMap.get("completed"), expectedDataMap.get("completed"));
        softAssert.assertEquals(actualDataMap.get("title"), expectedDataMap.get("title"));
        softAssert.assertEquals(response.getHeader("Via"), expectedDataMap.get("Via"));
        softAssert.assertEquals(response.getHeader("Server"), expectedDataMap.get("Server"));
        softAssert.assertAll();
    }
}