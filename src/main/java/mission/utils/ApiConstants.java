package mission.utils;

//  It holds fixed values like URLs, endpoints, content types, and keys so they don’t have to be repeated throughout the application
public final class ApiConstants {

    private ApiConstants() {}

    public static final String BASE_URL = "https://reqres.in/api";

    public static final String USERS = "/users";
    public static final String LOGIN = "/login";

    public static final String CONTENT_TYPE = "application/json";
    public static final String API_KEY = "reqres_4ac8715ef0684563939e8264ba6c6cd8";
}