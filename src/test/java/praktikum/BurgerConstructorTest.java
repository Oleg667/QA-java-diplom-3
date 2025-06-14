package praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class BurgerConstructorTest {

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    @Step("Инициализация и открытие браузера")
    public void setUp() {
        WebDriver driver = driverRule.getDriver();
        driver.get(EnvConfig.BASE_URL);
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void testNavigateToSaucesTab() {
        WebDriver driver = driverRule.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesTab();

        WebElement activeSaucesTab = mainPage.getActiveSaucesTab(); // заменено: теперь получаем активную вкладку через MainPage
        assertTrue("Раздел 'Соусы' должен быть активным", activeSaucesTab.isDisplayed());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void testNavigateToFillingsTab() {
        WebDriver driver = driverRule.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsTab();

        WebElement activeFillingsTab = mainPage.getActiveFillingsTab(); // заменено: теперь получаем активную вкладку через MainPage
        assertTrue("Раздел 'Начинки' должен быть активным", activeFillingsTab.isDisplayed());
    }

    @Test
    @DisplayName("Переход к разделу 'Булки' после перехода на другую вкладку")
    public void testNavigateToBunsTab() {
        WebDriver driver = driverRule.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        MainPage mainPage = new MainPage(driver);

        // Сначала переключимся на другой раздел, например, "Соусы"
        mainPage.clickSaucesTab();
        mainPage.getActiveSaucesTab(); // заменено: ожидание через метод MainPage вместо явного локатора

        // Теперь снова кликнем на "Булки"
        mainPage.clickBunsTab();

        WebElement activeBunsTab = mainPage.getActiveBunsTab(); // заменено: теперь получаем активную вкладку через MainPage
        assertTrue("Раздел 'Булки' должен быть активным после переключения", activeBunsTab.isDisplayed());
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driverRule.getDriver() != null) {
            driverRule.getDriver().quit();
        }
    }
}
