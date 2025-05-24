package serialization_deserialization;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import de_serialization.*;
import RestAssured.BaseClass;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GorestApiTests extends BaseClass {
    private RequestSpecification gorestSpecification;

    @BeforeAll
    public void setup(){
        //for gorest webpage
        RestAssured.baseURI = "https://gorest.co.in";
        gorestSpecification = new RequestSpecBuilder()
                .setBasePath("/public/v2")
                .addHeader("Authorization", getToken())
                .setContentType(ContentType.JSON)
                .build().log().all();
    }

    @Test
    public void createPostForRandomUserAndVerifyItsCreation(){
        //get all posts
        List<Post> posts = RestAssured
                .given()
                .spec(gorestSpecification)
                .when()
                .get("/posts")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract().jsonPath().getList(".", Post.class);

        assertFalse(posts.isEmpty(),"Posts list shouldn't be empty");

        int randomUser_id = posts.get(new Random().nextInt(posts.size())).user_id();
        System.out.println("Selected userId "+randomUser_id);
        Post newPost = new Post(0,randomUser_id,"New post by ARMINE","Some text");

        //create new post for selected user
        Post createdPost = RestAssured
                .given()
                .spec(gorestSpecification)
                .body(newPost)
                .when()
                .post("/users/"+randomUser_id+"/posts")
                .then()
                .statusCode(201)
                .extract().as(Post.class);

        assertEquals(randomUser_id,createdPost.user_id());
        assertEquals("New post by ARMINE",createdPost.title());

        //check that post was created
        List<Post> updatedPosts = RestAssured
                .given()
                .spec(gorestSpecification)
                .when()
                .get("/users/"+randomUser_id+"/posts")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList(".",Post.class);

        boolean isPostAvailable = updatedPosts.stream().anyMatch(p-> p.user_id() == createdPost.user_id());
        assertTrue(isPostAvailable, "The new post has been added to user's posts ");

    }

    @Test
    public void getAllUsersUpdateFirstUsersStatusWhichHaveInactiveStatus(){
        //get all users
        List<User> users = RestAssured
                .given()
                .spec(gorestSpecification)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList(".", User.class);

        //find first inactive user
        Optional<User> firstInactiveUserOptional = users
                .stream()
                .filter(u-> u.status().equals("inactive"))
                .findFirst();

        assertTrue(firstInactiveUserOptional.isPresent(),"There should be at least one inactive user");

        User firstInactiveUser = firstInactiveUserOptional.get();

        //update user status from inactive to active
        User updatedUser = new User(
                firstInactiveUser.id(),
                firstInactiveUser.name(),
                firstInactiveUser.email(),
                firstInactiveUser.gender(),
                "active"
        );

        User responseUser = RestAssured
                .given()
                .spec(gorestSpecification)
                .body(updatedUser)
                .when()
                .put("/users/"+firstInactiveUser.id())
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(User.class);

        assertEquals("active",responseUser.status());


    }
}
