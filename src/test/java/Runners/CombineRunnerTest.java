package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/*
 * Purpose:
 * This runner executes both UI and API feature files together
 * and generates a single consolidated Cucumber report.
 * 
 * Used when we want one execution and one report
 * covering end-to-end automation (UI + API).
 *
 * Dependencies:
 * Picks up step definitions from UI, API, and Base packages
 * Uses TestNG for execution control
 */
@CucumberOptions(
        features = "src/test/resources/Features",

        // Glue includes both UI and API step definitions
        glue = {
                "Steps.UI",
                "Steps.API",
                "Base"
        },

        plugin = {
                "pretty",

                // Single HTML report for combined UI + API execution
                "html:target/cucumber/combined-cucumber-report.html",

        },

        monochrome = true
)
public class CombineRunnerTest extends AbstractTestNGCucumberTests {

    /* Controls parallel execution at scenario level.     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
