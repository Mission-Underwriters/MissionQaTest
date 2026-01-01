package Client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/*
 * Purpose:
 * This class acts as a Client layer for ReqRes APIs.
 * All API request logic is centralized here so that
 * step definitions remain clean and readable.
 *
 * - Used by API step definition classes to make HTTP calls
 * - Depends on RestAssured for request/response handling
 */
public class ReqResClient {

    private static final String BASE_URI = "https://reqres.in";

    // Constructor sets the base URI once for all API calls
    public ReqResClient() {
        RestAssured.baseURI = BASE_URI;
    }

    /* ================= GET USERS ================= */

    // Fetch users by page number
    public Response getUsers(int page) {
        return RestAssured
                .given()
                .queryParam("page", page)
                .get("/api/users");
    }

    // Fetch users with delayed response
    public Response getDelayedUsers(int delay) {
        return RestAssured
                .given()
                .queryParam("delay", delay)
                .get("/api/users");
    }

    /* ================= SINGLE USER ================= */

    // Fetch single user details using user id
    public Response getUserById(int id) {
        return RestAssured
                .given()
                .get("/api/users/" + id);
    }

    /* ================= CREATE USER ================= */

    // Create a new user with name and job
    public Response createUser(String name, String job) {

        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("job", job);

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/users");
    }

    /* ================= LOGIN ================= */

    // Login API call
    // Used for both successful and unsuccessful login scenarios
    public Response login(String email, String password) {

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);

        // Password is optional: Present for success, omitted for negative scenarios
        if (password != null && !password.isEmpty()) {
            body.put("password", password);
        }

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/login");
    }
}
