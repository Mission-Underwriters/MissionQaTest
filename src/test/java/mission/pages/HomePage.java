package mission.pages;

import mission.base.BasePage;
import mission.utils.LoadProp;

public class HomePage extends BasePage {

    public static void homePage() {
        driver.get(LoadProp.getProperty("url"));
    }
}
