package mission.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProp {

    private static Properties prop;
    private static FileInputStream input;
    private static final String TESTDATA_PATH = "/src/test/resources/TestData/TestData.properties";
    private static final File CURRENT_DIR = new File(System.getProperty("user.dir"));

    /**
     * Get property value from TestData.properties by key
     * @param key The property key
     * @return value as String
     */
    public static String getProperty(String key) {
        prop = new Properties();
        try {
            input = new FileInputStream(CURRENT_DIR + TESTDATA_PATH);
            prop.load(input);
            input.close();
        } catch (IOException e) {
            System.out.println("Unable to read property file: " + e.getMessage());
        }
        return prop.getProperty(key);
    }
}
