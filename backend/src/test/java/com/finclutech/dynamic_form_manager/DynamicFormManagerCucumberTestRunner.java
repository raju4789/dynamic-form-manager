package com.finclutech.dynamic_form_manager;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to feature files
        glue = "com.finclutech.dynamic_form_manager.steps", // Path to step definitions
        plugin = {
                "pretty", // Console output
                "json:target/cucumber-report.json", // JSON report
                "html:target/cucumber-reports.html" // HTML report
        },
        monochrome = true // Clean console output
)
@ContextConfiguration(classes = com.finclutech.dynamic_form_manager.config.cucumber.CucumberSpringConfiguration.class)
public class DynamicFormManagerCucumberTestRunner {
}