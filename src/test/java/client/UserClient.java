package client;

import io.restassured.response.Response;
import model.LoginData;
import model.User;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private static final String CREATE_USER_PATH = "/api/auth/register";
    private static final String LOGIN_USER_PATH = "/api/auth/login";
    private static final String DELETE_USER_PATH = "/api/auth/user";

    public Response createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .post(CREATE_USER_PATH);
    }

    public Response loginUser(LoginData loginData) {
        return given()
                .spec(getBaseSpec())
                .body(loginData)
                .post(LOGIN_USER_PATH);
    }

    public Response deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .delete(DELETE_USER_PATH);
    }
}
