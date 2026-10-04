package praktikum;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pageobject.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = praktikum.BrowserFactory.createDriver();

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