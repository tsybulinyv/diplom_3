package praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.xpath(
            "//label[normalize-space()='Email']/following-sibling::input"
    );

    private final By restoreButton = By.xpath(
            "//button[normalize-space()='Восстановить']"
    );

    private final By loginLink = By.xpath(
            "//a[normalize-space()='Войти']"
    );

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть страницу восстановления пароля")
    public void open() {
        driver.get(
                "https://stellarburgers.education-services.ru/forgot-password"
        );
    }

    @Step("Ввести email для восстановления пароля")
    public void enterEmail(String email) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailInput)
        ).sendKeys(email);
    }

    @Step("Нажать кнопку «Восстановить»")
    public void clickRestoreButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(restoreButton)
        ).click();
    }

    @Step("Перейти ко входу из формы восстановления пароля")
    public void clickLoginLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }
}