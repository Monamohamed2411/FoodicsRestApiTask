import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.HashMap;
import java.util.Map;


public class TC03_UpdateRequestTest extends BaseClass {

    @Test
    public void UpdateRequest(){
        SoftAssert softAssert = new SoftAssert();
        System.out.println("the user updated ID "+ TC01_PostRequestTest.userId);
        try {
            Map<String, Object> UserData = new HashMap<>();
            UserData.put("name", "Mona Mohamed");
            UserData.put("job", "Software Tester");
            UserData.put("age", 27);
            JSONObject request = new JSONObject(UserData);

            Response PutResponse = RestAssured
            .given()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(request.toString())
                    .pathParams("id", TC01_PostRequestTest.userId)
            .when()
                    .put("/users/{id}")
            .then()
                    .log().all()
                    .extract().response();
            int statusCode = PutResponse.getStatusCode();

            if (statusCode == 404) {
                Reporter.log("the User does not exist", true);
                softAssert.fail("the User does not exist!");
            }
            if (statusCode != 200) {
                Reporter.log("no response from API: " + statusCode, true);
                softAssert.fail("no response from API: " + statusCode);
            }
            else {
                Reporter.log("the user is exist successfully with Status Code: " + statusCode);
            }
            softAssert.assertEquals(PutResponse.jsonPath().getString("name"), "Mona Mohamed");
            softAssert.assertEquals(PutResponse.jsonPath().getString("job"), "Software Tester");
            softAssert.assertEquals(PutResponse.jsonPath().getInt("age"), 27);
            Reporter.log("the user data updated successfully with user ID: " + TC01_PostRequestTest.userId);


        }catch (Exception e){
            softAssert.fail("the exception: " + e.getMessage());
            Reporter.log("the exception " + e.getMessage(), true);
        }
        softAssert.assertAll();




    }

}
