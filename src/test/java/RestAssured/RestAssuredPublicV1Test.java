package RestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestAssuredPublicV1Test {

    private RequestSpecification requestSpecification;

    @BeforeAll
    public void setup() {
        RestAssured.baseURI = "https://gorest.co.in";
        requestSpecification = new RequestSpecBuilder()
                .setBasePath("/public/v1")
                .addHeader("Authorization", "Bearer fc93ba365fb022bd8736d5c76f000ff19910ea395115bca048147caab9a80ec0")
                .build().log().all();
    }

    @Test
    public void getUsers() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/users")
                .then()
                .log()
                .body()
                .statusCode(200)
                .body("data.size()", equalTo(10));
    }

    @Test
    public void getUsersPerPage() {
        given()
                .spec(requestSpecification)
                .queryParam("per_page", 10)
                .when()
                .get("/users")
                .then()
                .log()
                .body()
                .statusCode(200)
                .body("data.size()", equalTo(10));
    }

    @Test
    public void deleteUserAndVerify() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/users")
                .then()
                .log()
                .body()
                .statusCode(200)
                .body("data.gender", everyItem(anyOf(equalTo("male"), equalTo("female"))));
    }
}
