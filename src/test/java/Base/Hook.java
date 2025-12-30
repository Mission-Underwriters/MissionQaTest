package Base;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hook {

	@Before
	public void setUp() {
	    DriverFactory.initDriver(LoadProp.getBrowser());
	    DriverFactory.getDriver().get(LoadProp.getUrl());
	}


    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
