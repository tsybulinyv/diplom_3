package praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.xpath(
            "//label[normalize-space()='Email']/following-sibling::input"
    );

    private final By passwordInput = By.name("Пароль");

    private final By loginButton = By.xpath(
            "//button[normalize-space()='Войти']"
    );

    private final By registerLink = By.xpath(
            "//a[normalize-space()='Зарегистрироваться']"
    );

    private final By forgotPasswordLink = By.xpath(
            "//a[contains(normalize-space(), 'Восстановить пароль')]"
    );

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть страницу входа")
    public void open() {
        driver.get(
                "https://stellarburgers.education-services.ru/login"
        );
    }

    @Step("Ввести email")
    public void enterEmail(String email) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailInput)
        ).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        ).sendKeys(password);
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLoginButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    @Step("Выполнить вход")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Дождаться перехода на главную страницу")
    public void waitForMainPage() {
        wait.until(
                ExpectedConditions.urlToBe(
                        "https://stellarburgers.education-services.ru/"
                )
        );
    }

    @Step("Перейти к регистрации")
    public void clickRegisterLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerLink)
        ).click();
    }

    @Step("Перейти к восстановлению пароля")
    public void clickForgotPasswordLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(forgotPasswordLink)
        ).click();
    }
}