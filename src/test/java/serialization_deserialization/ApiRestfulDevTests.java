package serialization_deserialization;

import de_serialization.ApiObject;
import de_serialization.DeviceDatas;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import RestAssured.BaseClass;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiRestfulDevTests extends BaseClass {
    private RequestSpecification apiRestSpecification;

    @BeforeAll
    public void setup(){
        //for restfulApi
        RestAssured.baseURI = "https://api.restful-api.dev";
        apiRestSpecification = new RequestSpecBuilder()
                .setBasePath("/objects")
                .setContentType(ContentType.JSON)
                .build().log().all();
    }

    @Test
    public void getAllDataAndDeseialize(){
        Response response = RestAssured
                .given()
                .spec(apiRestSpecification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<ApiObject> objects = response.as(new TypeRef<List<ApiObject>>() {});
        assertFalse(objects.isEmpty(), "response list should not be empty");

        assertTrue(objects.size() > 0, "Object list should not be empty");

        for (ApiObject object : objects) {
            System.out.println("ID: " + object.getId());
            System.out.println("Name: " + object.getName());
            if (object.getData() != null) {
                DeviceDatas data = object.getData();
                if (data.getColor() != null) System.out.println("Color: " + data.getColor());
                if (data.getCapacity() != null) System.out.println("Capacity: " + data.getCapacity());
                if (data.getCapacityGB() != 0) System.out.println("CapacityGB: " + data.getCapacityGB());
                if (data.getPrice() != 0.0f) System.out.println("Price: " + data.getPrice());
                if (data.getGeneration() != null) System.out.println("Generation: " + data.getGeneration());
                if (data.getYear() != 0) System.out.println("Year: " + data.getYear());
                if (data.getCpuModel() != null) System.out.println("CPU Model: " + data.getCpuModel());
                if (data.getHardDiskSize() != null) System.out.println("Hard Disk Size: " + data.getHardDiskSize());
                if (data.getStrapColour() != null) System.out.println("Strap Colour: " + data.getStrapColour());
                if (data.getCaseSize() != null) System.out.println("Case Size: " + data.getCaseSize());
                if (data.getDescription() != null) System.out.println("Description: " + data.getDescription());
                if (data.getScreenSize() != 0.0f) System.out.println("Screen Size: " + data.getScreenSize());
            }
            System.out.println();
        }

    }
}
