package mission.api;

import io.restassured.response.Response;
import mission.utils.ApiConstants;

import java.util.HashMap;
import java.util.Map;

// This class prepares login information (like email and password) and sends it to the login API so a user can sign in.
public class LoginService {

	private final RestClient client;

	public LoginService(RestClient client) {
		this.client = client;
	}

	//performs a login request. It sends the provided login details to the login API endpoint and returns the server's response.
	public Response login(Map<String, String> payload) {
		return client.post(ApiConstants.LOGIN, payload);
	}

	//performs a login request. It sends the provided login details only if the values are valid and then sends that data to the login API.
	public Response login(String email, String password) {
		Map<String, String> payload = new HashMap<>();
		if (email != null && !email.trim().isEmpty()) {
            payload.put("email", email.trim());
        }
		if (password != null && !password.trim().isEmpty() && !password.equalsIgnoreCase("[empty]")) {
            payload.put("password", password.trim());
        }
		return login(payload);
	}
}