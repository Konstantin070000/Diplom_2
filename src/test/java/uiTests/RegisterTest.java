package uiTests;

import client.UserClient;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.Assert.assertTrue;

public class RegisterTest extends BaseUiTest {

    private final UserClient userClient = new UserClient();
    private String email;
    private final String password = "password123";

    @Test
    public void registerWithValidPasswordShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        email = "test" + System.currentTimeMillis() + "@mail.com";

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registerPage.register("TestUser", email, password);

        assertTrue("После успешной регистрации должна отображаться кнопка входа",
                loginPage.isLoginButtonDisplayed());
    }

    @Test
    public void registerWithShortPasswordShouldShowError() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        email = "test" + System.currentTimeMillis() + "@mail.com";

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registerPage.register("TestUser", email, "12345");

        assertTrue("Должно появиться сообщение об ошибке пароля",
                registerPage.isPasswordErrorDisplayed());
    }

    @After
    public void deleteUser() {
        if (email != null) {
            User user = new User(email, password, "TestUser");
            Response loginResponse = userClient.loginUser(user);

            if (loginResponse.statusCode() == 200) {
                String accessToken = loginResponse.jsonPath().getString("accessToken");
                userClient.deleteUser(accessToken);
            }
        }
    }
}