package com.finclutech.dynamic_form_manager.steps;

import io.cucumber.java.en.Then;

import static org.hamcrest.Matchers.notNullValue;

public class AuthenticationSteps {

    private final CommonSteps commonSteps;

    public AuthenticationSteps(CommonSteps commonSteps) {
        this.commonSteps = commonSteps;
    }

    @Then("the response should contain a valid JWT token")
    public void theResponseShouldContainAValidJwtToken() {
        // Assert that the response contains a valid JWT token
        commonSteps.getResponse().then().body("data.token", notNullValue());
    }
}