package mission.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import mission.utils.DriverFactory;
import mission.utils.LoadProp;

public class Hook {

    @Before
    public void setUp(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverFactory.initDriver(LoadProp.getProperty("Browser"));
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverFactory.quitDriver();
        }
    }
}