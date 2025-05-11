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
public class RestAssuredPublicV2Test extends BaseClass{

    private RequestSpecification requestSpecification;

    @BeforeAll
    public void setup() {
        RestAssured.baseURI = "https://gorest.co.in";
        requestSpecification = new RequestSpecBuilder()
                .setBasePath("/public/v2")
                .addHeader("Authorization", getToken())
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
                .body("size()", equalTo(10));
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
                .body("size()", equalTo(10));
    }

    @Test
    public void deleteUserAndVerify() {
        int userId = given()
                .spec(requestSpecification)
                .queryParam("per_page", 1)
                .when()
                .get("/users")
                .then()
                .log()
                .body()
                .extract()
                .path("[0].id");

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
                .statusCode(404);
    }
}
