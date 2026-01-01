package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/*
 * Purpose:
 * This runner is used to execute UI automation scenarios
 * related to SauceDemo application.
 *
 * It covers:
 * Login scenarios, Product, cart, checkout, and logout flows
 *
 * UI tests are grouped together to keep browser-based execution and isolated from API tests.
 */
@CucumberOptions(
        features = {
                // Main UI functionality scenarios
                "src/test/resources/Features/UI-Test.feature",
        },

        // Glue contains UI step definitions and base hooks
        glue = {
                "Steps.UI",
                "Base"
        },

        plugin = {
                "pretty",

                // HTML report for UI execution
                "html:target/cucumber/ui-cucumber-report.html",
        },

        monochrome = true
)public class UIRunnerTest extends AbstractTestNGCucumberTests {

    /* Controls scenario-level parallel execution. */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}