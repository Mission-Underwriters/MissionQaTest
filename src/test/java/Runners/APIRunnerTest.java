package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/*
 * Purpose:
 * This runner is responsible for executing only API feature files.
 *
 * It is kept separate from UI runners so that:
 * 
 * API tests can be executed independently
 * Parallel execution can be safely enabled
 * 
 */
@CucumberOptions(
        features = "src/test/resources/Features/API-Test.feature",

        // Glue contains only API step definitions
        glue = {
                "Steps.API"
        },

        plugin = {
                "pretty",

                // HTML report for API execution summary
                "html:target/cucumber/api-cucumber-report.html",

        },

        monochrome = true
)
public class APIRunnerTest extends AbstractTestNGCucumberTests {

    /*
     * Enables parallel execution at scenario level.
     * Safe for API tests as there is no shared browser state.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
