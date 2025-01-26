package com.finclutech.dynamic_form_manager;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
@ContextConfiguration(classes = com.finclutech.dynamic_form_manager.config.cucumber.CucumberSpringConfiguration.class)

public class DynamicFormManagerCucumberTestRunner {
}
