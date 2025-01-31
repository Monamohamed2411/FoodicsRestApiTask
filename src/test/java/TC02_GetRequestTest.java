import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Reporter;


public class TC02_GetRequestTest extends BaseClass
{
    @Test
    public void GetRequest() {
        SoftAssert softAssert = new SoftAssert();
try {
    Response getResponse = RestAssured

       .given()
            .header("Content-Type", "application/json")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .pathParams("id", TC01_PostRequestTest.userId)
       .when()
            .get("/users/{id}")
       .then()
            .log().all()
            .extract().response();
    int statusCode = getResponse.getStatusCode();
    if (statusCode == 404) {
        Reporter.log("the User does not exist", true);
        softAssert.fail("the User does not exist!");
    }
    if (statusCode != 200) {
        Reporter.log("no response from API: " + statusCode, true);
        softAssert.fail("no response from API: " + statusCode);
    }
    else {
        Reporter.log("the user exist successfully with Status Code: " + statusCode);
    }
    softAssert.assertEquals(getResponse.jsonPath().getString("name"), "mona","name mismatch");
    softAssert.assertEquals(getResponse.jsonPath().getString("job"), "tester", "job mismatch");
    softAssert.assertEquals(getResponse.jsonPath().getInt("age"), 30, "Age mismatch");
    Reporter.log("the user data exist successfully with user ID: " + TC01_PostRequestTest.userId);
}
catch (Exception e){
    softAssert.fail("the exception: " + e.getMessage());
    Reporter.log("the exception " + e.getMessage(), true);
}
        softAssert.assertAll();

    }


}
