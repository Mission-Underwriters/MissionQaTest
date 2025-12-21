package mission.hooks;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import mission.utils.DriverFactory;
import mission.utils.LoadProp;

public class Hook {

    @Before
    public void setUp() {
        String browser = LoadProp.getProperty("Browser");
        DriverFactory.initDriver(browser);
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}