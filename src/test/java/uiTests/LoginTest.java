package uiTests;

import client.UserClient;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseUiTest {

    private final UserClient userClient = new UserClient();
    private String accessToken;
    private String email;
    private final String password = "password123";

    @Before
    public void createUser() {
        email = "testlogin" + System.currentTimeMillis() + "@mail.com";
        User user = new User(email, password, "TestUser");

        Response response = userClient.createUser(user);
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    public void loginFromMainPageButtonShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);

        assertTrue("После логина должна отображаться кнопка 'Оформить заказ'",
                mainPage.isPlaceOrderButtonDisplayed());
    }

    @Test
    public void loginFromPersonalAccountShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickPersonalAccount();
        loginPage.login(email, password);

        assertTrue("После логина должна отображаться кнопка 'Оформить заказ'",
                mainPage.isPlaceOrderButtonDisplayed());
    }

    @Test
    public void loginFromRegisterPageShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registerPage.clickLoginLink();
        loginPage.login(email, password);

        assertTrue("После логина должна отображаться кнопка 'Оформить заказ'",
                mainPage.isPlaceOrderButtonDisplayed());
    }

    @Test
    public void loginFromForgotPasswordPageShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

        mainPage.clickLoginButton();
        loginPage.clickForgotPasswordLink();
        forgotPasswordPage.clickLoginLink();
        loginPage.login(email, password);

        assertTrue("После логина должна отображаться кнопка 'Оформить заказ'",
                mainPage.isPlaceOrderButtonDisplayed());
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}