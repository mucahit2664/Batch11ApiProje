package com.techproed;

import Pojos.BookingDatesPojo;
import Pojos.BookingPojo;
import Pojos.BookingResponsePojo;
import TestBase.TestBaseHerOkuApp;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*;
public class PostRequestWithPojo02 extends TestBaseHerOkuApp {
     /*
	 	When
	 		I send POST Request to the Url https://restful-booker.herokuapp.com/booking
	 		with the request body {
								    "firstname": "Eric",
								    "lastname": "Ericson",
								    "totalprice": 900,
								    "depositpaid": flase,
								    "bookingdates": {
								        "checkin": "2021-01-07",
								        "checkout": "2021-01-25"
								     }
								  }
	 	Then
	 		Status code is 200
	 		And response body should be like {
											    "bookingid": 13,
											    "booking": {
											       "firstname": "Eric",
								                 "lastname": "Ericson",
								                  "totalprice": 900,
								               "depositpaid": faLse,
								    "bookingdates": {
								        "checkin": "2021-01-07",
								        "checkout": "2021-01-25"
											        }
											    }
											 }
	 */
     @Test
     public void postWithPojo(){

         //spec02.pathParam("bookingPath", "booking");

         // Data olustur
         BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2020-09-09", "2020-09-21");
         BookingPojo expectedPojoData = new BookingPojo("Chris", "Brown", 11111, true, bookingDatesPojo);

         // Request gonder
         Response response =
                 given().contentType(ContentType.JSON).
                         spec(spec02).
                         body(expectedPojoData).
                         when().
                         post();

         response.prettyPrint();

         // 1. yol body- pojo
         response.then().assertThat().statusCode(200)
                 .body("booking.firstname", equalTo(expectedPojoData.getFirstname()),
                         "booking.lastname",equalTo(expectedPojoData.getLastname()),
                         "booking.totalprice",equalTo(expectedPojoData.getTotalprice()),
                         "booking.depositpaid",equalTo (expectedPojoData.isDepositpaid()),
                         "booking.bookingdates.checkin",equalTo(expectedPojoData.getBookingdates().getCheckin()),
                         "booking.bookingdates.checkout",equalTo(expectedPojoData.getBookingdates().getCheckout()));
         // 2. yol jsonPath ve pojo
         JsonPath json=response.jsonPath();
         Assert.assertEquals(expectedPojoData.getFirstname(),json.getString("booking.firstname"));
         Assert.assertEquals(expectedPojoData.getLastname(),json.getString("booking.lastname"));
         Assert.assertEquals(expectedPojoData.getTotalprice(),json.getInt("booking.totalprice"));
         Assert.assertEquals(expectedPojoData.isDepositpaid(),json.getBoolean("booking.depositpaid"));
         Assert.assertEquals(expectedPojoData.getBookingdates().getCheckin(),json.getString("booking.bookingdates.checkin"));
         Assert.assertEquals(expectedPojoData.getBookingdates().getCheckout(),json.getString("booking.bookingdates.checkout"));
         // Gson
         // Assertion hata verir. Cunku BookingPojo'nun formatı gelen response dan farklı
         // Bu tarz problemlere karsılasılasılırsa ya-- Gson kullanmıyacağız-- jsonPah ve Body
         //                                      ya da yeni bir Pojo Class oluturup response tan gelenleri
         //                                      ekleyecegim.

         // BookingPojo actualBooking = response.as(BookingPojo);
         // Failure---------------------------------

         BookingResponsePojo actualBooking = response.as(BookingResponsePojo.class);
         System.out.println(actualBooking);

         Assert.assertEquals(expectedPojoData.getFirstname(),actualBooking.getBooking().getFirstname());
         Assert.assertEquals(expectedPojoData.getLastname(),actualBooking.getBooking().getLastname());
         Assert.assertEquals(expectedPojoData.getTotalprice(),actualBooking.getBooking().getTotalprice());
         Assert.assertEquals(expectedPojoData.isDepositpaid(),actualBooking.getBooking().isDepositpaid());
         Assert.assertEquals(expectedPojoData.getBookingdates().getCheckin(),actualBooking.getBooking().getBookingdates().getCheckin());
         Assert.assertEquals(expectedPojoData.getBookingdates().getCheckout(),actualBooking.getBooking().getBookingdates().getCheckout());

     }
}
