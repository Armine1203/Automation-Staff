package RestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestAssuredPublicApiTest {

    private RequestSpecification requestSpecification;

    @BeforeAll
    public void setup() {
        RestAssured.baseURI = "https://gorest.co.in";
        requestSpecification = new RequestSpecBuilder()
                .setBasePath("/public-api")
                .addHeader("Authorization", "Bearer fc93ba365fb022bd8736d5c76f000ff19910ea395115bca048147caab9a80ec0")
                .build().log().all();
    }

    @Order(1)
    @Test
    public void getUsers() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/users")
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(200)
                .body("data.size()", equalTo(10));
    }

    @Order(2)
    @Test
    public void getUsersPerPage() {
        System.out.println("test2");
        given()
                .spec(requestSpecification)
                .queryParam("per_page", 10)
                .when()
                .get("/users")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.size()", equalTo(10));
    }

    @Order(3)
    @Test
    public void deleteUserAndVerify() {
        int userId =
                given()
                .spec(requestSpecification)
                .queryParam("per_page", 1)
                .when()
                .get("/users")
                .then()
                .log().body()
                .extract().path("data[0].id");

        given().spec(requestSpecification)
                .when()
                .delete("/users/" + userId)
                .then()
                .log()
                .body()
                .statusCode(anyOf(equalTo(204), equalTo(200)));

        given().spec(requestSpecification)
                .when()
                .get("/users/" + userId)
                .then()
                .log()
                .body()
                .statusCode(200)//return 200 but in body code=404
                .body("code", is(404));  // But the response code in body is 404


    }
}
