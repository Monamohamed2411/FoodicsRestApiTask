import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseClass {
    private static final String BASE_URL = "https://reqres.in/api";
    @BeforeTest
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }
}
