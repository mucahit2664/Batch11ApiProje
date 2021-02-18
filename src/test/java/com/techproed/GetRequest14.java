package com.techproed;
import TestBase.TestBaseDummy;
import TestData.DummyTestData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.util.*;
import static io.restassured.RestAssured.*;

public class GetRequest14 extends TestBaseDummy {
    /*
          When
              I send a request to http://dummy.restapiexample.com/api/v1/employees
          Then
              Status code is 200
              And the highest salary is 725000
              And the minimum age is 19
              And the second highest salary is 675000
     */
    @Test
    public void get01(){
        // Url olustur
        spec03.pathParam("employeesPath","employees");

        // Beklenen Datayı olustur
        DummyTestData expectedObj = new DummyTestData();
        Map<String,Integer> expectedDataMap = expectedObj.setUpData2();
        System.out.println(expectedDataMap);

        // 4. request olustur
        Response response = given().
                spec(spec03).
                when().
                get("/{employeesPath}");
        //   response.prettyPrint();
        // 4. Assertion
        // 1.yol jsonPath
        JsonPath json = response.jsonPath();
        // status code u assert edelim // Integer                    // int
        Assert.assertEquals(expectedDataMap.get("Status code"),(Integer)response.getStatusCode());
        // En yuksek maasi assert edelim.
        List<String> salaryList = json.getList("data.employee_salary");
        List<Integer> salaryListInt = new ArrayList<>();
        salaryList.stream().forEach(t->salaryListInt.add(Integer.valueOf(t)));
        Collections.sort(salaryListInt);
        Assert.assertEquals(expectedDataMap.get("EnYuksekMaas"),salaryListInt.get(salaryListInt.size()-1));
        // En kucuk yas
        List<String> ageList = json.getList("data.employee_age");
        List<Integer> ageListInt = new ArrayList<>();
        ageList.stream().forEach(t->ageListInt.add(Integer.valueOf(t)));
        Collections.sort(ageListInt);
        Assert.assertEquals(expectedDataMap.get("EnKucukYas"),ageListInt.get(0));

        // En yuksek ikinci maasi assert edelim
        Assert.assertEquals(expectedDataMap.get("IkinciYuksekMaas"),salaryListInt.get(salaryListInt.size()-2));

        // 2. yol De-Serialization ---> GSON
        Map<String,Object> actualDataMap = response.as(HashMap.class);
        System.out.println(actualDataMap);

        // En yuksek maas
        List<String> salaryListGson = new ArrayList<>();
        int numOfEmployee = ((List)actualDataMap.get("data")).size();
        for (int i=0; i<numOfEmployee;i++){
            salaryListGson.add((String)((Map)((List)actualDataMap.get("data")).get(i)).get("employee_salary"));
        }
        List<Integer> salaryListGsonInt = new ArrayList<>();
        salaryListGson.stream().forEach(t->salaryListGsonInt.add(Integer.valueOf(t)));
        Collections.sort(salaryListGsonInt);
        Assert.assertEquals(expectedDataMap.get("EnYuksekMaas"),salaryListGsonInt.get(salaryListGsonInt.size()-1));
        // En kucuk yas
        List<String>ageListGson = new ArrayList<>();
        for (int i=0; i<numOfEmployee;i++){
            ageListGson.add((String)((Map)((List)actualDataMap.get("data")).get(i)).get("employee_age"));
        }
        List<Integer> ageListGsonInt = new ArrayList<>();
        ageListGson.stream().forEach(t->ageListGsonInt.add(Integer.valueOf(t)));
        Collections.sort(ageListGsonInt);
        Assert.assertEquals(expectedDataMap.get("EnKucukYas"),ageListGsonInt.get(0));

        // En yuksek ikinci maasi assert edelim
        Assert.assertEquals(expectedDataMap.get("IkinciYuksekMaas"),salaryListGsonInt.get(numOfEmployee-2));


    }
}
