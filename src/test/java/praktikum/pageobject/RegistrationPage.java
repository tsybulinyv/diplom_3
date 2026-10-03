package praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameInput = By.xpath(
            "//label[normalize-space()='Имя']/following-sibling::input"
    );

    private final By emailInput = By.xpath(
            "//label[normalize-space()='Email']/following-sibling::input"
    );

    private final By passwordInput = By.name("Пароль");

    private final By registerButton = By.xpath(
            "//button[normalize-space()='Зарегистрироваться']"
    );

    private final By loginLink = By.xpath(
            "//a[normalize-space()='Войти']"
    );

    private final By passwordError = By.xpath(
            "//p[@class='input__error text_type_main-default' and normalize-space()='Некорректный пароль']"
    );

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть страницу регистрации")
    public void open() {
        driver.get(
                "https://stellarburgers.education-services.ru/register"
        );
    }

    @Step("Ввести имя")
    public void enterName(String name) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameInput)
        ).sendKeys(name);
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

    @Step("Нажать кнопку «Зарегистрироваться»")
    public void clickRegisterButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();
    }

    @Step("Заполнить форму регистрации")
    public void fillRegistrationForm(
            String name,
            String email,
            String password
    ) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
    }

    @Step("Зарегистрировать пользователя")
    public void register(
            String name,
            String email,
            String password
    ) {
        fillRegistrationForm(name, email, password);
        clickRegisterButton();
    }

    @Step("Дождаться перехода на страницу входа")
    public void waitForLoginPage() {
        wait.until(
                ExpectedConditions.urlContains("/login")
        );
    }

    @Step("Перейти ко входу")
    public void clickLoginLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }

    @Step("Проверить сообщение о некорректном пароле")
    public boolean isPasswordErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordError)
        ).isDisplayed();
    }
}