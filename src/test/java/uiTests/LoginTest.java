package uiTests;

import org.junit.Before;
import org.junit.Test;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseUiTest {

    private final String email = "testlogin@example.com";
    private final String password = "password123";

    @Before
    public void openLoginPage() {
        driver.get(baseUrl);
    }

    @Test
    public void loginFromMainPageButtonShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);

        assertTrue("После логина должен отображаться текст Конструктор",
                driver.getPageSource().contains("Конструктор"));
    }

    @Test
    public void loginFromPersonalAccountShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickPersonalAccount();
        loginPage.login(email, password);

        assertTrue("После логина должен отображаться текст Конструктор",
                driver.getPageSource().contains("Конструктор"));
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

        assertTrue("После логина должен отображаться текст Конструктор",
                driver.getPageSource().contains("Конструктор"));
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

        assertTrue("После логина должен отображаться текст Конструктор",
                driver.getPageSource().contains("Конструктор"));
    }
}