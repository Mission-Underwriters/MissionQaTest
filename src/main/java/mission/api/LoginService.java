package mission.api;

import io.restassured.response.Response;
import mission.utils.ApiConstants;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

	private final RestClient client;

	public LoginService(RestClient client) {
		this.client = client;
	}

	public Response login(Map<String, String> payload) {
		return client.post(ApiConstants.LOGIN, payload);
	}

	public Response login(String email, String password) {

		Map<String, String> payload = new HashMap<>();
		

		if (email != null && !email.trim().isEmpty()) {
            payload.put("email", email.trim());
        }

		if (password != null && !password.trim().isEmpty()
                && !password.equalsIgnoreCase("[empty]")) {
            payload.put("password", password.trim());
        }
		System.out.println("Login payload: " + payload);
		return login(payload);
	}
}
