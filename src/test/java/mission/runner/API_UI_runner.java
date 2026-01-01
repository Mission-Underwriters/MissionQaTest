package mission.runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Used to run UI tests written in Cucumber
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"mission.stepDefs", "mission.hook"},
        tags="@ui or @api",
        plugin = {"pretty",
        		"html:target/cucumber-html/report.html",
        		"json:target/cucumber-json/report.json"}
)

//It is to act as a test runner that starts the execution of Cucumber UI tests using TestNG.

public class API_UI_runner extends AbstractTestNGCucumberTests {
	
}