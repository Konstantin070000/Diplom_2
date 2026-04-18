package tests;

import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.LoginData;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class UserTest {

    private final UserClient userClient = new UserClient();
    private String accessToken;
    private String createdAccessToken;
    private User loginUser;

    @Before
    public void setUp() {
        loginUser = UserGenerator.getRandomUser();

        Response createResponse = userClient.createUser(loginUser);
        accessToken = createResponse.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверяем, что можно успешно создать нового уникального пользователя")
    public void createUniqueUserShouldReturnSuccess() {
        User user = UserGenerator.getRandomUser();

        Response response = userClient.createUser(user);
        createdAccessToken = response.jsonPath().getString("accessToken");

        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Создание дублирующего пользователя")
    @Description("Проверяем, что повторное создание уже существующего пользователя возвращает ошибку")
    public void createDuplicateUserShouldReturnError() {
        Response secondResponse = userClient.createUser(loginUser);

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
        User user = new User(UserGenerator.getRandomUser().getEmail(), null, "TestUser");

        Response response = userClient.createUser(user);

        assertEquals(SC_FORBIDDEN, response.statusCode());
        assertEquals("Email, password and name are required fields", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверяем, что создание пользователя без имени возвращает ошибку")
    public void createUserWithoutNameShouldReturnError() {
        User user = new User(UserGenerator.getRandomUser().getEmail(), "password123", null);

        Response response = userClient.createUser(user);

        assertEquals(SC_FORBIDDEN, response.statusCode());
        assertEquals("Email, password and name are required fields", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    @Description("Проверяем, что существующий пользователь может успешно авторизоваться")
    public void loginUserShouldReturnSuccess() {
        LoginData loginData = UserGenerator.getLoginData(loginUser);
        Response response = userClient.loginUser(loginData);

        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Проверяем, что при неверном пароле возвращается ошибка авторизации")
    public void loginWithWrongPasswordShouldReturnError() {
        LoginData wrongLoginData = new LoginData(loginUser.getEmail(), "wrongPassword");
        Response response = userClient.loginUser(wrongLoginData);

        assertEquals(SC_UNAUTHORIZED, response.statusCode());
        assertEquals("email or password are incorrect", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Проверяем, что при неверном email возвращается ошибка авторизации")
    public void loginWithWrongEmailShouldReturnError() {
        LoginData wrongLoginData = new LoginData("wrong_" + loginUser.getEmail(), loginUser.getPassword());
        Response response = userClient.loginUser(wrongLoginData);

        assertEquals(SC_UNAUTHORIZED, response.statusCode());
        assertEquals("email or password are incorrect", response.jsonPath().getString("message"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
        if (createdAccessToken != null) {
            userClient.deleteUser(createdAccessToken);
        }
    }
}