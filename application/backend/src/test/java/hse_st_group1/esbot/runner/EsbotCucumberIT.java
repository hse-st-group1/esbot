package hse_st_group1.esbot.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "hse_st_group1.esbot.steps",
    plugin = {"pretty", "summary", "html:target/cucumber-report.html"},
    monochrome = true
)
public class EsbotCucumberIT {
    
}
