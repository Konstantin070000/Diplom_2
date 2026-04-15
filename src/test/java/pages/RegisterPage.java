package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final By passwordErrorText = By.xpath("//p[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void setName(String name) {
        type(nameField, name);
    }

    public void setEmail(String email) {
        type(emailField, email);
    }

    public void setPassword(String password) {
        type(passwordField, password);
    }

    public void clickRegisterButton() {
        click(registerButton);
    }

    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    public boolean isPasswordErrorDisplayed() {
        return isElementDisplayed(passwordErrorText);
    }

    public void clickLoginLink() {
        click(loginLink);
    }
}