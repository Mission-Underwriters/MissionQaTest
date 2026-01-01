package mission.api;

import io.restassured.response.Response;
import java.util.function.Supplier;

// Utility class that retries an API call if it fails.
public final class RetryUtils {

    private RetryUtils() {}

    //It retries an API call until it succeeds or runs out of attempts
    public static Response retry(
            Supplier<Response> action,
            int maxAttempts,
            long delayMillis
    ) {
        Response response = null;

        for (int i = 1; i <= maxAttempts; i++) {
            response = action.get();
            if (response.getStatusCode() == 200) {
                return response;
            }
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException ignored) {}
        }
        throw new RuntimeException("Request failed after retries");
    }
}