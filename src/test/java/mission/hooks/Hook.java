package mission.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import mission.utils.DriverFactory;
import mission.utils.LoadProp;

public class Hook {

    @Before
    public void setUp(Scenario scenario) {

        // Start browser only for UI scenarios
        if (scenario.getSourceTagNames().contains("@ui")) {
            String browser = LoadProp.getProperty("Browser");
            DriverFactory.initDriver(browser);
        }
    }

    @After
    public void tearDown(Scenario scenario) {

        // Quit browser only for UI scenarios
        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverFactory.quitDriver();
        }
    }
}
