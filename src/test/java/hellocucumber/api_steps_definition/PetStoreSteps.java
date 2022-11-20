package hellocucumber.api_steps_definition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetStoreSteps {
    String baseUrl = "https://petstore.swagger.io/v2";
    String username;
    Response response;
    RequestSpecification request;

    @Given("I want to create new user")
    public void create_new_user() {
        username = String.valueOf(System.currentTimeMillis());
        RestAssured.baseURI = baseUrl;
        RestAssuredConfig config = RestAssuredConfig
                .config()
                .httpClient(HttpClientConfig
                        .httpClientConfig()
                        .setParam("http.connection.timeout", 30000));
        request = RestAssured.given().config(config);
        request.header("Content-type", "application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("firstName", "Test");
        requestParams.put("lastName", "User");
        requestParams.put("email", username + "@mail.org");
        requestParams.put("password", "Test@" + username);
        requestParams.put("phone", "+6212344567");
        requestParams.put("userStatus", 1);

        request.body(requestParams.toString());
        response = request.post("/user").then().extract().response();
        assertEquals(200, response.statusCode());
    }

    @When("verify user {string} in database")
    public void get_user_by_username(String status) {
        RestAssured.baseURI = baseUrl;
        RestAssuredConfig config = RestAssuredConfig
                .config()
                .httpClient(HttpClientConfig
                        .httpClientConfig()
                        .setParam("http.connection.timeout", 30000));
        request = RestAssured.given().config(config);
        request.header("Content-type", "application/json");

        response = request.get("/user/" + username).then().extract().response();

        if (status.equals("exist")) {
            assertEquals(200, response.statusCode());
        } else {
            assertEquals(404, response.statusCode());
        }
    }

    @When("I login as user")
    public void login_as_user() {
        RestAssured.baseURI = baseUrl;
        RestAssuredConfig config = RestAssuredConfig
                .config()
                .httpClient(HttpClientConfig
                        .httpClientConfig()
                        .setParam("http.connection.timeout", 30000));
        request = RestAssured.given().config(config);
        request.header("Content-type", "application/json");

        response = request.get("/user/login?username=" + username + "&password=Test@" + username).then().extract().response();
        assertEquals(200, response.statusCode());
    }

    @Then("I login successfully")
    public void check_login_success() {
        assertTrue(response.asString().contains("logged in"));
    }

    @When("I delete user")
    public void delete_user() {
        RestAssured.baseURI = baseUrl;
        RestAssuredConfig config = RestAssuredConfig
                .config()
                .httpClient(HttpClientConfig
                        .httpClientConfig()
                        .setParam("http.connection.timeout", 30000));
        request = RestAssured.given().config(config);
        request.header("Content-type", "application/json");

        response = request.delete("/user/" + username).then().extract().response();
        assertEquals(200, response.statusCode());
    }
}
