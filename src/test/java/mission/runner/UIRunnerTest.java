package mission.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"mission.stepDefs", "mission.hook"},
        tags= "@ui",
        plugin = {"pretty","html:target/cucumber-html/ui.html",
        					"json:target/cucumber-json/ui.json"},
        monochrome = true
)

public class UIRunnerTest extends AbstractTestNGCucumberTests { }