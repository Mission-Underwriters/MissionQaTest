package Base;

import java.io.InputStream;
import java.util.Properties;

/*
 * Purpose:
 * This class loads configuration values from TestData.properties file.
 *
 * Here used encapsulation with the Property loading logic is which is hidden inside this class
 * Properties are loaded once using static block
 */
public final class LoadProp {

    private static final Properties properties = new Properties();

    // Static block loads properties only once during class loading
    static {
        try (InputStream input = LoadProp.class
                .getClassLoader()
                .getResourceAsStream("Config/TestData.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Config/TestData.properties not found under src/test/resources"
                );
            }
            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    // Private constructor to prevent object creation
    private LoadProp() {}

    // Returns browser name from properties
    public static String getBrowser() {
        return getRequired("browser");
    }

    // Returns application URL from properties
    public static String getUrl() {
        return getRequired("url");
    }

    // Validates required property values
    private static String getRequired(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(key + " key is missing in TestData.properties");
        }
        return value.trim();
    }
}
