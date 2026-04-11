package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {

        if ("yandex".equalsIgnoreCase(browser)) {

            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");

            // ВАЖНО: указываем версию драйвера вручную
            WebDriverManager.chromedriver()
                    .browserVersion("144")
                    .setup();

            return new ChromeDriver(options);
        }

        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}