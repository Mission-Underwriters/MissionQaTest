package Base;

import java.io.InputStream;
import java.util.Properties;

public final class LoadProp {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = LoadProp.class
                .getClassLoader()
                .getResourceAsStream("Config/TestData.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "TestData/TestData.properties not found under src/test/resources"
                );
            }
            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    private LoadProp() {}

    public static String getBrowser() {
        return getRequired("browser");
    }

    public static String getUrl() {
        return getRequired("url");
    }

    private static String getRequired(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(key + " key is missing in TestData.properties");
        }
        return value.trim();
    }
}
