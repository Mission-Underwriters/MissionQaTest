package mission.utils;

import java.io.InputStream;
import java.util.Properties;

public class LoadProp {

    private static Properties prop = new Properties();

    static {
        try (InputStream input = LoadProp.class.getClassLoader().getResourceAsStream("testdata/TestData.properties")) {
        	
            if (input == null) {
                throw new RuntimeException("TestData.properties not found");
            }
            prop.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load TestData.properties", e);
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}