import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;


@Test
public class TC01_PostRequestTest extends BaseClass{
    public static int userId;

    public void PostRequestTest(){

        try {

            Map<String, Object> UserData = new HashMap<>();
            UserData.put("name", "mona");
            UserData.put("job", "tester");
            UserData.put("age", 30);
            JSONObject request = new JSONObject(UserData);

            Response postResponse =RestAssured
              .given()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(request.toString())
              .when()
                    .post("/users")
              .then()
                    .log().all()
                    .extract().response();
            userId = postResponse.jsonPath().getInt("id");

            int statusCode = postResponse.getStatusCode();
            if (statusCode !=201){
                Assert.fail("the creating is fail, returned Status code: " + statusCode);
            }
            Assert.assertEquals(postResponse.jsonPath().getString("name"), "mona");
            Assert.assertEquals(postResponse.jsonPath().getString("job"), "tester");
            Assert.assertEquals(postResponse.jsonPath().getInt("age"), 30);
        }catch (Exception e){
            Assert.fail("the exception: " + e.getMessage());
        }


    }
}
