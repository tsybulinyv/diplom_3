package praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButton = By.xpath(
            "//button[normalize-space()='Войти в аккаунт']"
    );

    private final By personalAccountButton = By.cssSelector(
            "a[href='/account']"
    );

    private final By bunsSection = By.xpath(
            "//span[normalize-space()='Булки']/parent::div"
    );

    private final By saucesSection = By.xpath(
            "//span[normalize-space()='Соусы']/parent::div"
    );

    private final By fillingsSection = By.xpath(
            "//span[normalize-space()='Начинки']/parent::div"
    );

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть главную страницу Stellar Burgers")
    public void open() {
        driver.get(
                "https://stellarburgers.education-services.ru/"
        );
    }

    @Step("Нажать кнопку «Войти в аккаунт»")
    public void clickLoginButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    @Step("Нажать кнопку «Личный кабинет»")
    public void clickPersonalAccount() {
        wait.until(
                ExpectedConditions.elementToBeClickable(personalAccountButton)
        ).click();
    }

    @Step("Перейти в раздел «Булки»")
    public void clickBuns() {
        wait.until(
                ExpectedConditions.elementToBeClickable(bunsSection)
        ).click();
    }

    @Step("Перейти в раздел «Соусы»")
    public void clickSauces() {
        wait.until(
                ExpectedConditions.elementToBeClickable(saucesSection)
        ).click();
    }

    @Step("Перейти в раздел «Начинки»")
    public void clickFillings() {
        wait.until(
                ExpectedConditions.elementToBeClickable(fillingsSection)
        ).click();
    }

    @Step("Проверить, что активен раздел «Булки»")
    public boolean isBunsActive() {
        return wait.until(
                ExpectedConditions.attributeContains(
                        bunsSection,
                        "class",
                        "tab_tab_type_current"
                )
        ) != null;
    }

    @Step("Проверить, что активен раздел «Соусы»")
    public boolean isSaucesActive() {
        return wait.until(
                ExpectedConditions.attributeContains(
                        saucesSection,
                        "class",
                        "tab_tab_type_current"
                )
        ) != null;
    }

    @Step("Проверить, что активен раздел «Начинки»")
    public boolean isFillingsActive() {
        return wait.until(
                ExpectedConditions.attributeContains(
                        fillingsSection,
                        "class",
                        "tab_tab_type_current"
                )
        ) != null;
    }
}