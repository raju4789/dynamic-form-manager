package com.finclutech.dynamic_form_manager;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "com.finclutech.dynamic_form_manager.steps"
)
public class DynamicFormManagerCucumberTest {
}
