package uiTests;

import org.junit.Test;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest extends BaseUiTest {

    @Test
    public void clickSaucesTabShouldSwitchToSaucesSection() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSauces();

        assertTrue("После перехода в Соусы страница должна содержать текст Соусы",
                driver.getPageSource().contains("Соусы"));
    }

    @Test
    public void clickFillingsTabShouldSwitchToFillingsSection() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickFillings();

        assertTrue("После перехода в Начинки страница должна содержать текст Начинки",
                driver.getPageSource().contains("Начинки"));
    }

    @Test
    public void clickBunsTabShouldSwitchToBunsSection() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSauces();
        mainPage.clickBuns();

        assertTrue("После перехода в Булки страница должна содержать текст Булки",
                driver.getPageSource().contains("Булки"));
    }
}