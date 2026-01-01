package mission.stepDefs;

import io.restassured.response.Response;
import org.testng.Assert;

// Common helper methods for API step definitions. 
public class API_CommonStepDefs {

    protected static Response lastResponse;

    // Saves the given API response as the latest response.
    protected void setResponse(Response response) {
        lastResponse = response;
    }

    // Returns the most recent API response.
    protected Response getResponse() {
        return lastResponse;
    }

    // it checks whether the request succeeded or failed as expected.
    protected void verifyStatusCode(int expectedStatus) {
        Assert.assertEquals(lastResponse.getStatusCode(), expectedStatus, "Unexpected HTTP status code");
    }
    
    // This ensures the server returned data in the expected format. If not, the full response body is shown to help with debugging.
    protected void assertJsonResponse() {
        String contentType = lastResponse.getContentType();
        Assert.assertTrue(contentType.contains("application/json"),
        				"Expected JSON but got: " + contentType +
        				"\nResponse:\n" + lastResponse.getBody().asString()
        );
    }
}