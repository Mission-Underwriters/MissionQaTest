package mission.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mission.utils.ApiConstants;

import static io.restassured.RestAssured.given;

public class RestClient {

    public RestClient() {
        RestAssured.baseURI = ApiConstants.BASE_URL;
    }

    private RequestSpecification baseRequest() {
        return given()
                .contentType(ApiConstants.CONTENT_TYPE)
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer <session_token>");
    }


    public Response get(String endpoint) {
        return baseRequest()
                .when()
                .get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return baseRequest()
                .body(body)
                .when()
                .post(endpoint);
    }
}
