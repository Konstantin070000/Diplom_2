package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    private final By bunsTab = By.xpath("//span[text()='Булки']");
    private final By saucesTab = By.xpath("//span[text()='Соусы']");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickPersonalAccount() {
        driver.findElement(personalAccountButton).click();
    }

    public void clickConstructor() {
        driver.findElement(constructorButton).click();
    }

    public void clickBuns() {
        driver.findElement(bunsTab).click();
    }

    public void clickSauces() {
        driver.findElement(saucesTab).click();
    }

    public void clickFillings() {
        driver.findElement(fillingsTab).click();
    }
}