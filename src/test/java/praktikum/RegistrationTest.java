package praktikum;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import praktikum.pageobject.RegistrationPage;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class RegistrationTest {

    private final String browser;

    private WebDriver driver;
    private RegistrationPage registrationPage;

    private final UserApi userApi = new UserApi();

    private String email;
    private final String password = "password123";

    private boolean registeredUser;

    public RegistrationTest(String browser) {
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
        RestAssured.baseURI =
                "https://stellarburgers.education-services.ru";

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

        registrationPage = new RegistrationPage(driver);

        email =
                "test" + System.currentTimeMillis() + "@yandex.ru";
    }

    @After
    public void tearDown() {
        if (registeredUser) {
            UserModel user = new UserModel(
                    email,
                    password,
                    "Test User"
            );

            Response loginResponse = userApi.loginUser(user);

            if (loginResponse.statusCode() == 200) {
                String accessToken = loginResponse.path("accessToken");

                if (accessToken != null) {
                    userApi.deleteUser(accessToken);
                }
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Успешная регистрация")
    public void successfulRegistrationTest() {
        registrationPage.open();

        registrationPage.register(
                "Test User",
                email,
                password
        );

        registeredUser = true;

        registrationPage.waitForLoginPage();

        assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
    }

    @Test
    @Description("Регистрация с некорректным паролем")
    public void registrationWithInvalidPasswordTest() {
        registrationPage.open();

        registrationPage.register(
                "Test User",
                email,
                "12345"
        );

        assertTrue(
                registrationPage.isPasswordErrorDisplayed()
        );
    }
}