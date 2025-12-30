package mission.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"mission.stepDefs", "mission.hook"},
        tags= "@api",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)
public class APIRunnerTest extends AbstractTestNGCucumberTests {

}
