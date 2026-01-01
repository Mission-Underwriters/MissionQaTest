package mission.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Utility class used to read data from a properties file.
public class LoadProp {

    private static Properties prop;
    private static FileInputStream input;
    private static final String TESTDATA_PATH = "/src/test/resources/TestData/TestData.properties";
    private static final File CURRENT_DIR = new File(System.getProperty("user.dir"));

    // Look inside the file and give me the value for key
    public static String getProperty(String key) {
        prop = new Properties();
        try {
            input = new FileInputStream(CURRENT_DIR + TESTDATA_PATH);
            prop.load(input);
            input.close();
        } catch (IOException e) {
        	throw new RuntimeException("Unable to read property file", e);
        }
        return prop.getProperty(key);
    }
}