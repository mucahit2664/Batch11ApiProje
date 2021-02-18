package TestData;

        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

public class HerOkuAppTestData {
   Map<String, String> bookingDatesMap = new HashMap<>();
   Map<String, Object> bookingDetailsMap = new HashMap<>();

   public Map<String, Object> setUpData() {
      bookingDatesMap.put("checkin", "2019-01-11");
      bookingDatesMap.put("checkout", "2020-04-28");

      bookingDetailsMap.put("firstname", "Mark");
      bookingDetailsMap.put("lastname", "Smith");
      bookingDetailsMap.put("totalprice", 452);
      bookingDetailsMap.put("depositpaid", false);
      bookingDetailsMap.put("bookingDates", bookingDatesMap);
      return bookingDetailsMap;
   }

   public JSONObject setUpDataJSONObject() {
      JSONObject bookingDatesJSONObject = new JSONObject();
      bookingDatesJSONObject.put("checkin", "2020-09-09");
      bookingDatesJSONObject.put("checkout", "2020-09-21");

      JSONObject bookingDetailsJSONObject = new JSONObject();
      bookingDetailsJSONObject.put("firstname", "Selim");
      bookingDetailsJSONObject.put("lastname", "Ak");
      bookingDetailsJSONObject.put("totalprice", 11111);
      bookingDetailsJSONObject.put("depositpaid", true);
      bookingDetailsJSONObject.put("bookingdates", bookingDatesJSONObject);

      return bookingDetailsJSONObject;


   }
}



