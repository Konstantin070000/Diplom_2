package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailField = By.xpath("//input[@name='name']");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setEmail(String email) {
        type(emailField, email);
    }

    public void setPassword(String password) {
        type(passwordField, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    public void clickRegisterLink() {
        click(registerLink);
    }

    public void clickForgotPasswordLink() {
        click(forgotPasswordLink);
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }
}