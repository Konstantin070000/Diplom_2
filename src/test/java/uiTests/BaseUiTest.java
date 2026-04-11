package uiTests;

import driver.DriverFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class BaseUiTest {

    protected WebDriver driver;
    protected final String baseUrl = "https://stellarburgers.education-services.ru/";

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver(System.getProperty("browser", "chrome"));
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}