package mission.api;

import io.restassured.response.Response;
import mission.utils.ApiConstants;

import java.util.Map;

public class UserService {

    private final RestClient client;

    public UserService(RestClient client) {
        this.client = client;
    }

    public Response getUsersByPage(int page) {
        return client.get(ApiConstants.USERS + "?page=" + page);
    }

    public Response getSingleUser(int userId) {
        return client.get(ApiConstants.USERS + "/" + userId);
    }

    public Response createUser(String name, String job) {
        return client.post(
                ApiConstants.USERS,
                Map.of("name", name, "job", job)
        );
    }
}
