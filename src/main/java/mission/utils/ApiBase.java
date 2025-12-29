package mission.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class ApiBase {

	    protected Response response;

	    public ApiBase() {
	        RestAssured.baseURI = ApiConstants.BASE_URL;
	    }
	}
