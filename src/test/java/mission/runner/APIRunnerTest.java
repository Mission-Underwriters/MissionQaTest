package mission.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"mission.stepDefs", "mission.hook"},
        tags= "@api",
        plugin = {"pretty","html:target/cucumber-html/api.html",
        					"json:target/cucumber-json/api.json"},
        monochrome = true
)
public class APIRunnerTest extends AbstractTestNGCucumberTests {

}
