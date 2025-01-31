import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
        softAssert.fail("User does not exist!");
    }
    if (statusCode != 200) {
        softAssert.fail("no response from API: " + statusCode);
    }
    softAssert.assertEquals(getResponse.jsonPath().getString("name"), "mona","name mismatch");
    softAssert.assertEquals(getResponse.jsonPath().getString("job"), "tester", "job mismatch");
    softAssert.assertEquals(getResponse.jsonPath().getInt("age"), 30, "Age mismatch");
    softAssert.assertEquals(getResponse.jsonPath().getInt("id"), TC01_PostRequestTest.userId, "id mismatch");

    System.out.println("the user updated ID "+ TC01_PostRequestTest.userId);
    int ActualId = getResponse.jsonPath().getInt("data.id");
    System.out.println(" Retrieved User ID: " + ActualId);
}
catch (Exception e){
    softAssert.fail("the exception: " + e.getMessage());
}

    }


}
