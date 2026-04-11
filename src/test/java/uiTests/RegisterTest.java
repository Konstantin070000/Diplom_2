package uiTests;

import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.Assert.assertTrue;

public class RegisterTest extends BaseUiTest {

    @Test
    public void registerWithValidPasswordShouldBeSuccessful() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        registerPage.register("TestUser", email, "password123");

        // Проверяем, что появилась кнопка "Войти"
        assertTrue("Должна отображаться кнопка входа",
                driver.getPageSource().contains("Войти"));
    }

    @Test
    public void registerWithShortPasswordShouldShowError() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        registerPage.register("TestUser", email, "12345");

        assertTrue("Должно появиться сообщение об ошибке пароля",
                registerPage.isPasswordErrorDisplayed());
    }

}