package praktikum;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class BrowserFactory {

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome");

        if ("yandex".equalsIgnoreCase(browser)) {
            return createYandexDriver();
        }

        if ("chrome".equalsIgnoreCase(browser)) {
            return new ChromeDriver();
        }

        throw new IllegalArgumentException(
                "Неизвестный браузер: " + browser
        );
    }

    private static WebDriver createYandexDriver() {
        ChromeOptions options = new ChromeOptions();

        options.setBinary(
                "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe"
        );

        ChromeDriverService service =
                new ChromeDriverService.Builder()
                        .usingDriverExecutable(
                                new File("C:\\WebDriver\\chromedriver.exe")
                        )
                        .build();

        return new ChromeDriver(service, options);
    }
}