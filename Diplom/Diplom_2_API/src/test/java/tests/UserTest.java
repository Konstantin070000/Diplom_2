package tests;

import client.UserClient;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private final UserClient userClient = new UserClient();
    private String accessToken;

    @Test
    public void createUniqueUserShouldReturnSuccess() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        Response response = userClient.createUser(user);
        accessToken = response.jsonPath().getString("accessToken");

        assertEquals(200, response.statusCode());
    }

    @Test
    public void createDuplicateUserShouldReturnError() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        Response firstResponse = userClient.createUser(user);
        accessToken = firstResponse.jsonPath().getString("accessToken");

        Response secondResponse = userClient.createUser(user);

        assertEquals(403, secondResponse.statusCode());
        assertEquals("User already exists", secondResponse.jsonPath().getString("message"));
    }

    @Test
    public void createUserWithoutEmailShouldReturnError() {
        User user = new User(null, "password123", "TestUser");

        Response response = userClient.createUser(user);

        assertEquals(403, response.statusCode());
        assertEquals("Email, password and name are required fields", response.jsonPath().getString("message"));
    }

    @Test
    public void loginUserShouldReturnSuccess() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        // создаём пользователя
        userClient.createUser(user);

        // логинимся
        Response response = userClient.loginUser(user);

        accessToken = response.jsonPath().getString("accessToken");

        assertEquals(200, response.statusCode());
    }

    @Test
    public void loginWithWrongPasswordShouldReturnError() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        // создаём пользователя
        userClient.createUser(user);

        // неправильный пароль
        User wrongUser = new User(email, "wrongPassword", "TestUser");

        Response response = userClient.loginUser(wrongUser);

        assertEquals(401, response.statusCode());
        assertEquals("email or password are incorrect", response.jsonPath().getString("message"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}