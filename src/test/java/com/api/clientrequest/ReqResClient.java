package com.api.clientrequest;

import com.api.pojo.CreateRecord;
import io.restassured.RestAssured;

import javax.xml.ws.Response;

import static io.restassured.RestAssured.given;

/**
 * @author myname
 */
public class ReqResClient {
    public ReqResClient() {
        RestAssured.baseURI = "https://reqres.in";
    }

    public Response getUsersByPage(int page) {
        return given()
                .when()
                .get("/api/users?page=" + page)
                .then()
                .extract()
                .response();
    }

    public Response getUserById(String userId) {
        return given()
                .when()
                .get("/api/users/" + userId)
                .then()
                .extract()
                .response();
    }

    public Response createUser(CreateRecord request) {
        return given()
                .header("Content-Type", "application/json")
                .body(request)
                .when()
                .post("/api/users")
                .then()
                .extract()
                .response();
    }

    public Response loginUnsuccessful(String email, String password) {
        return given()
                .header("Content-Type", "application/json")
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                .when()
                .post("/api/login")
                .then()
                .extract()
                .response();
    }
}


