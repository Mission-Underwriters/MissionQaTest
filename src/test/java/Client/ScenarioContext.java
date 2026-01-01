package Client;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

/*
 * Purpose:
 * ScenarioContext is a shared storage class used in API automation.
 * It allows data (like API responses or values extracted from responses)
 * to be stored and accessed across different step definitions
 * within the same test execution.
 *
 * - Step 1: Store API response after making a request
 * - Step 2: Retrieve the same response in validation steps
 *
 * This avoids passing objects between step methods
 * and keeps step definitions clean and readable.
 */
public class ScenarioContext {

	// Holds the latest API response for the current scenario
	private static Response response;

	// Generic key-value storage for sharing data between steps
	private static final Map<String, Object> context = new HashMap<>();

	/*
	 * Stores the API response so it can be validated in subsequent step
	 * definitions.
	 */
	public static void setResponse(Response res) {
		response = res;
	}

	/*
	 * Returns the stored API response. Commonly used in Then steps for assertions.
	 */
	public static Response getResponse() {
		return response;
	}

	/*
	 * Stores any value using a logical key. Example: userId, token,
	 * totalUsersCount, etc.
	 */
	public static void set(String key, Object value) {
		context.put(key, value);
	}

	/*
	 * Retrieves a stored value using its key. Casting is handled by the calling
	 * step if required.
	 */
	public static Object get(String key) {
		return context.get(key);
	}
}
