package tests;

import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.LoginData;
import model.User;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

public class UserTest {

    private final UserClient userClient = new UserClient();
    private String accessToken;

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверяем, что можно успешно создать нового уникального пользователя")
    public void createUniqueUserShouldReturnSuccess() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        Response response = userClient.createUser(user);
        accessToken = response.jsonPath().getString("accessToken");

        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Создание дублирующего пользователя")
    @Description("Проверяем, что повторное создание уже существующего пользователя возвращает ошибку")
    public void createDuplicateUserShouldReturnError() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        Response firstResponse = userClient.createUser(user);
        accessToken = firstResponse.jsonPath().getString("accessToken");

        Response secondResponse = userClient.createUser(user);

        assertEquals(SC_FORBIDDEN, secondResponse.statusCode());
        assertEquals("User already exists", secondResponse.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Проверяем, что создание пользователя без email возвращает ошибку")
    public void createUserWithoutEmailShouldReturnError() {
        User user = new User(null, "password123", "TestUser");

        Response response = userClient.createUser(user);

        assertEquals(SC_FORBIDDEN, response.statusCode());
        assertEquals("Email, password and name are required fields", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверяем, что создание пользователя без пароля возвращает ошибку")
    public void createUserWithoutPasswordShouldReturnError() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, null, "TestUser");

        Response response = userClient.createUser(user);

        assertEquals(SC_FORBIDDEN, response.statusCode());
        assertEquals("Email, password and name are required fields", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверяем, что создание пользователя без имени возвращает ошибку")
    public void createUserWithoutNameShouldReturnError() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", null);

        Response response = userClient.createUser(user);

        assertEquals(SC_FORBIDDEN, response.statusCode());
        assertEquals("Email, password and name are required fields", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    @Description("Проверяем, что существующий пользователь может успешно авторизоваться")
    public void loginUserShouldReturnSuccess() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        Response createResponse = userClient.createUser(user);
        accessToken = createResponse.jsonPath().getString("accessToken");

        LoginData loginData = new LoginData(email, "password123");
        Response response = userClient.loginUser(loginData);

        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Проверяем, что при неверном пароле возвращается ошибка авторизации")
    public void loginWithWrongPasswordShouldReturnError() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, "password123", "TestUser");

        Response createResponse = userClient.createUser(user);
        accessToken = createResponse.jsonPath().getString("accessToken");

        LoginData wrongLoginData = new LoginData(email, "wrongPassword");
        Response response = userClient.loginUser(wrongLoginData);

        assertEquals(SC_UNAUTHORIZED, response.statusCode());
        assertEquals("email or password are incorrect", response.jsonPath().getString("message"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}