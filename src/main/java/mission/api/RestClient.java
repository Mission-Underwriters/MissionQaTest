package mission.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mission.utils.ApiConstants;

import static io.restassured.RestAssured.given;

// It sets up common request details (like headers and base URL) and provides easy-to-use methods to send GET, POST, PUT, and DELETE requests.
public class RestClient {

	//sets the base URL for all API requests
    public RestClient() {
        RestAssured.baseURI = ApiConstants.BASE_URL;
    }

    // It adds headers such as content type, user agent, and authorization so they don’t need to be repeated for every API call/scenario.
    private RequestSpecification baseRequest() {
        return given()
                .contentType(ApiConstants.CONTENT_TYPE)
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres_4ac8715ef0684563939e8264ba6c6cd8");
    }

    // GET Request: retrieve or read data from the server
    public Response get(String endpoint) {
        return baseRequest()
                .when()
                .get(endpoint);
    }

    // POST Request: send data to the server
    public Response post(String endpoint, Object body) {
        return baseRequest()
                .body(body)
                .when()
                .post(endpoint);
    }
    
 // POST Request: update existing data on the server
    public Response put(String endpoint, Object body) {
        return baseRequest()
                .body(body)
                .when()
                .put(endpoint);
    }

    // DELETE Request: remove data from the server
    public Response delete(String endpoint) {
        return baseRequest()
                .when()
                .delete(endpoint);
    }

}