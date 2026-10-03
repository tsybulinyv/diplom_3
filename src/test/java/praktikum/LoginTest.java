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
import praktikum.pageobject.ForgotPasswordPage;
import praktikum.pageobject.LoginPage;
import praktikum.pageobject.MainPage;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LoginTest {

    private final String browser;

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ForgotPasswordPage forgotPasswordPage;

    private final UserApi userApi = new UserApi();

    private String email;
    private final String password = "password123";
    private String accessToken;

    public LoginTest(String browser) {
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

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);

        email = "login" + System.currentTimeMillis() + "@yandex.ru";

        UserModel user = new UserModel(
                email,
                password,
                "Login Test User"
        );

        Response response = userApi.createUser(user);

        response.then()
                .statusCode(200);

        accessToken = response.path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userApi.deleteUser(accessToken);
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Вход через кнопку «Войти в аккаунт» на главной странице")
    public void loginFromMainPageTest() {
        mainPage.open();

        mainPage.clickLoginButton();

        loginPage.login(email, password);
        loginPage.waitForMainPage();

        assertTrue(
                driver.getCurrentUrl().endsWith("/")
        );
    }

    @Test
    @Description("Вход через кнопку «Личный кабинет»")
    public void loginFromPersonalAccountTest() {
        mainPage.open();

        mainPage.clickPersonalAccount();

        loginPage.login(email, password);
        loginPage.waitForMainPage();

        assertTrue(
                driver.getCurrentUrl().endsWith("/")
        );
    }

    @Test
    @Description("Вход через кнопку «Войти» в форме регистрации")
    public void loginFromRegistrationPageTest() {
        driver.get(
                "https://stellarburgers.education-services.ru/register"
        );

        new praktikum.pageobject.RegistrationPage(driver)
                .clickLoginLink();

        loginPage.login(email, password);
        loginPage.waitForMainPage();

        assertTrue(
                driver.getCurrentUrl().endsWith("/")
        );
    }

    @Test
    @Description("Вход через кнопку «Войти» в форме восстановления пароля")
    public void loginFromForgotPasswordPageTest() {
        forgotPasswordPage.open();

        forgotPasswordPage.clickLoginLink();

        loginPage.login(email, password);
        loginPage.waitForMainPage();

        assertTrue(
                driver.getCurrentUrl().endsWith("/")
        );
    }
}