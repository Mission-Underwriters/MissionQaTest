package mission.stepDefs;

import io.restassured.response.Response;
import org.testng.Assert;

public class API_CommonStepDefs {

    protected static Response lastResponse;

    protected void setResponse(Response response) {
        lastResponse = response;
    }

    protected Response getResponse() {
        return lastResponse;
    }

    protected void verifyStatusCode(int expectedStatus) {
        Assert.assertEquals(
                lastResponse.getStatusCode(),
                expectedStatus,
                "Unexpected HTTP status code"
        );
    }
    
    protected void assertJsonResponse() {
        String contentType = lastResponse.getContentType();
        Assert.assertTrue(
                contentType.contains("application/json"),
                "Expected JSON but got: " + contentType +
                "\nResponse:\n" + lastResponse.getBody().asString()
        );
    }

}
