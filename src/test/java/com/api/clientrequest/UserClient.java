package com.api.clientrequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
/**
 * @author myname
 */
public class UserClient {
    public Response getUsersByPage(int page) {
        return given()
                .baseUri("https://reqres.in")
                .basePath("/api/users")
                .queryParam("page", page)
                .when()
                .get()
                .then()
                .extract()
                .response();
    }
}
