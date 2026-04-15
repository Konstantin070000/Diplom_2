package uiTests;

import org.junit.Test;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest extends BaseUiTest {

    @Test
    public void clickSaucesTabShouldSwitchToSaucesSection() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSauces();

        assertTrue("После перехода вкладка 'Соусы' должна стать активной",
                mainPage.isSaucesTabActive());
    }

    @Test
    public void clickFillingsTabShouldSwitchToFillingsSection() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickFillings();

        assertTrue("После перехода вкладка 'Начинки' должна стать активной",
                mainPage.isFillingsTabActive());
    }

    @Test
    public void clickBunsTabShouldSwitchToBunsSection() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSauces();
        mainPage.clickBuns();

        assertTrue("После перехода вкладка 'Булки' должна стать активной",
                mainPage.isBunsTabActive());
    }
}