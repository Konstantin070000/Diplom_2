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
    private final By placeOrderButton = By.xpath("//button[text()='Оформить заказ']");

    private final By activeBunsTab = By.xpath("//div[contains(@class,'tab_tab_type_current')]//span[text()='Булки']");
    private final By activeSaucesTab = By.xpath("//div[contains(@class,'tab_tab_type_current')]//span[text()='Соусы']");
    private final By activeFillingsTab = By.xpath("//div[contains(@class,'tab_tab_type_current')]//span[text()='Начинки']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void clickPersonalAccount() {
        click(personalAccountButton);
    }

    public void clickConstructor() {
        click(constructorButton);
    }

    public void clickBuns() {
        click(bunsTab);
    }

    public void clickSauces() {
        click(saucesTab);
    }

    public void clickFillings() {
        click(fillingsTab);
    }

    public boolean isPlaceOrderButtonDisplayed() {
        return isElementDisplayed(placeOrderButton);
    }

    public boolean isBunsTabActive() {
        return isElementDisplayed(activeBunsTab);
    }

    public boolean isSaucesTabActive() {
        return isElementDisplayed(activeSaucesTab);
    }

    public boolean isFillingsTabActive() {
        return isElementDisplayed(activeFillingsTab);
    }
}