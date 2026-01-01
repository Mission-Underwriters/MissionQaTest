package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.InputStream;
import java.util.Properties;

/*
 * Purpose:
 * Central utility class for API automation configuration.
 *
 * Load API configuration from properties file
 * Create a reusable RestAssured RequestSpecification
 * Apply common headers and logging filters
 */

public class ApiUtils {

    // Shared request specification used by all API calls
    private static RequestSpecification requestSpec;

    /*
     * Static block executes once when class is loaded.
     * Ideal place for one-time API configuration setup.
     */
    static {
        try {
            Properties prop = new Properties();

            // Load API test data from classpath
            InputStream is = ApiUtils.class.getClassLoader()
                    .getResourceAsStream("config/TestData.properties");

            // Fail fast if configuration file is missing
            if (is == null) {
                throw new RuntimeException("TestData.properties not found");
            }

            prop.load(is);

            // Read base URL for API execution
            String baseUrl = prop.getProperty("api.baseUrl");

            /*
             * Build common RequestSpecification:
             * Base URI
             * Default headers
             * Request & response logging for debugging
             */
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(baseUrl)
                    .addHeader("Content-Type", "application/json")
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();

        } catch (Exception e) {
            // Stop test execution if API setup fails
            throw new RuntimeException("API setup failed", e);
        }
    }

    /*
     * Provides access to the shared RequestSpecification.
     * Used by API Client classes to execute requests.
     */
    public static RequestSpecification getRequestSpec() {
        return requestSpec;
    }
}
