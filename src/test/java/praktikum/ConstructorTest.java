package praktikum;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import praktikum.pageobject.MainPage;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorTest {

    private final String browser;

    private WebDriver driver;
    private MainPage mainPage;

    public ConstructorTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getBrowser() {
        return new Object[][]{
                {"Chrome"},
                {"Yandex"}
        };
    }

    @Before
    public void setUp() {
        if ("Chrome".equals(browser)) {
            driver = new ChromeDriver();
        } else {
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

            driver = new ChromeDriver(service, options);
        }

        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Переход к разделу «Булки»")
    public void bunsSectionTest() {
        mainPage.clickSauces();
        mainPage.clickBuns();

        assertTrue(
                mainPage.isBunsActive()
        );
    }

    @Test
    @Description("Переход к разделу «Соусы»")
    public void saucesSectionTest() {
        mainPage.clickSauces();

        assertTrue(
                mainPage.isSaucesActive()
        );
    }

    @Test
    @Description("Переход к разделу «Начинки»")
    public void fillingsSectionTest() {
        mainPage.clickFillings();

        assertTrue(
                mainPage.isFillingsActive()
        );
    }
}