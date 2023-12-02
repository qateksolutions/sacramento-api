package api_test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SimplePatchTest {
    private static final Logger LOGGER = LogManager.getLogger(SimplePatchTest.class);

    @Test
    public void updateUserSingleField() {
        LOGGER.info("----------API Test: Update User's single field---------");

        // Specify the base URL or endpoint of the REST API
        RestAssured.baseURI = "https://reqres.in/api/users";

        // Get the RequestSpecification of the Request that you want to send to the server.
        // The server is specified by the BaseURI that we have specified in the above.
        RequestSpecification httpRequest = RestAssured.given();

        Faker faker = new Faker();
        String userRole = faker.job().title();
        LOGGER.debug("User Job Title: " + userRole);

        // Creating the request body
        JSONObject reqBody = new JSONObject();
        reqBody.put("job", userRole);

        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(reqBody.toJSONString());

        // Make a request to the server by specifying the method type.
        // This will return the response from the server and store the response in a variable.
        String id = "2";
        Response response = httpRequest.request(Method.PATCH, id);
        LOGGER.debug(response.getBody().asPrettyString());

        // Assert that the correct status is returned.
        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath jsonPath = response.jsonPath();
        String actualRole = jsonPath.getString("job");
        Assert.assertEquals(actualRole, userRole);

        LOGGER.info("----------End Test: Update User's single field-------");
    }
}
