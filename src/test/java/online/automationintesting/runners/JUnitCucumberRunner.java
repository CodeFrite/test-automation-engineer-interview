package online.automationintesting.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "online.automationintesting.stepdefinitions",
    plugin = {"pretty", "html:src/test-reports/cucumber-report.html", "json:src/test-reports/cucumber-report.json"},
    tags = "not @ignore")
public class JUnitCucumberRunner {
}