package mission.api;

import io.restassured.response.Response;
import mission.utils.ApiConstants;

import java.util.Map;

// It handles all user-related API operations(i.e.: fetching user information, creating new users, updating existing users, and deleting users)
public class UserService {

	private final RestClient client;

	public UserService(RestClient client) {
		this.client = client;
	}

	// Retrieves a list of users for a specific page.
	public Response getUsersByPage(int page) {
		return client.get(ApiConstants.USERS + "?page=" + page);
	}

	// Retrieves details of a single user.
	public Response getSingleUser(int userId) {
		return client.get(ApiConstants.USERS + "/" + userId);
	}

	// Creates a new user in the system.
	public Response createUser(String name, String job) {
		return client.post(ApiConstants.USERS, Map.of("name", name, "job", job));
	}

	// Update an existing user in the system.
	public Response updateUser(int userId, Map<String, String> data) {
		return client.put(ApiConstants.USERS + "/" + userId, data);
	}

	//  Removes a user from the system.
	public Response deleteUser(int userId) {
		return client.delete(ApiConstants.USERS + "/" + userId);
	}

}