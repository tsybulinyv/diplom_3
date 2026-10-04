package praktikum;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pageobject.ForgotPasswordPage;
import praktikum.pageobject.LoginPage;
import praktikum.pageobject.MainPage;

import static org.junit.Assert.assertTrue;

public class LoginTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ForgotPasswordPage forgotPasswordPage;

    private final UserApi userApi = new UserApi();

    private String email;
    private final String password = "password123";
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI =
                "https://stellarburgers.education-services.ru";

        driver = praktikum.BrowserFactory.createDriver();

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