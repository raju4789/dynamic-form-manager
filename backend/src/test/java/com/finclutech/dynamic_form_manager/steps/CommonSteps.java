package com.finclutech.dynamic_form_manager.steps;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.notNullValue;

public class CommonSteps {

    private String request;
    private String token;
    private Response response;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port; // Use the random port
    }

    @Given("the user provides request:")
    public void theUserProvidesRequest(String reqBody) {
        request = reqBody;
    }

    @Given("the user has a valid token")
    public void theUserHasAValidToken() {
        // Obtain a valid token by calling the authentication endpoint
        token = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\": \"admin\", \"password\": \"admin@4789\"}")
                .post("/api/v1/auth/authenticate")
                .jsonPath()
                .getString("data.token");
        Assertions.assertNotNull(token, "Token should not be null");
    }

    @Given("the user has an invalid token")
    public void theUserHasAnInvalidToken() {
        // Set an invalid token
        token = "invalid_token";
    }

    @When("the user sends a GET request to {string}")
    public void theUserSendsAGetRequestTo(String endpoint) {
        // Send a GET request to the specified endpoint with the token
        response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .get(endpoint);
    }

    @Given("the user sends a GET request with no token to {string}")
    public void theUserSendsAGetRequestWithNoTokenTo(String endpoint) {
        // Send a GET request to the specified endpoint with the token
        response = RestAssured.given()
                .get(endpoint);
    }

    @When("the user sends a POST request to {string}")
    public void theUserSendsAPostRequestTo(String endpoint) {
        // Send a POST request to the specified endpoint
        response = RestAssured.given()
                .contentType("application/json")
                .body(request)
                .post(endpoint);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        // Assert the response status code
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain an error message")
    public void theResponseShouldContainAnErrorMessage() {
        // Assert that the response contains an error message
        response.then().body("errorDetails.errorMessage", notNullValue());
    }

    @Then("the response should contain error {string}")
    public void theResponseShouldContainError(String errorMessage) {
        // Assert that the response contains the specified error message
        Assert.assertEquals(errorMessage, String.valueOf(response.jsonPath().getString("errorDetails.errorMessage")));
    }

    public Response getResponse() {
        return response;
    }
}