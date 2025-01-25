package com.finclutech.dynamic_form_manager.steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;


public class FormManagementSteps {

    private final CommonSteps commonSteps;

    public FormManagementSteps(CommonSteps commonSteps) {
        this.commonSteps = commonSteps;
    }


    @Then("the response should has length of {int}")
    public void theResponseShouldHasLengthOf(int length) {
        Assert.assertEquals(length, commonSteps.getResponse().jsonPath().getList("data").size());

    }
}