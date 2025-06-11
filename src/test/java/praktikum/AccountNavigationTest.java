package praktikum;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class AccountNavigationTest {
    private final String email = "oleg-g@yandex.rus";
    private final String password = "667667667";

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    @Step("Инициализация и открытие браузера")
    public void setUp() {
        WebDriver driver = driverRule.getDriver();
        driver.get(EnvConfig.BASE_URL);
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Личный кабинет»")
    public void testNavigateToAccountPage() {
        WebDriver driver = driverRule.getDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        mainPage.clickAccountButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/account/profile"));
        assertTrue(driver.getCurrentUrl().contains("/account/profile"));

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Профиль не загрузился", profilePage.isProfileHeaderVisible());
        assertTrue("Кнопка выхода не найдена", profilePage.isLogoutButtonVisible());
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Конструктор» из профиля")
    public void testNavigateFromProfileToConstructor() {
        WebDriver driver = driverRule.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        mainPage.clickAccountButton();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Профиль не загрузился", profilePage.isProfileHeaderVisible());

        // Клик на "Конструктор"
        mainPage.clickConstructorLink();
        assertTrue("Не произошёл переход на главную через 'Конструктор'",
                mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка перехода по клику на логотип Stellar Burgers из профиля")
    public void testNavigateFromProfileToLogo() {
        WebDriver driver = driverRule.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        mainPage.clickAccountButton();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Профиль не загрузился", profilePage.isProfileHeaderVisible());

        // Клик на логотип
        mainPage.clickLogo();
        assertTrue("Не произошёл переход на главную через логотип",
                mainPage.isOrderButtonVisible());
    }


    @Test
    @DisplayName("Проверка выход по кнопке «Выйти» в личном кабинете")
    public void testLogoutFromProfilePage() {
        WebDriver driver = driverRule.getDriver();
        // Залогиниться и перейти в личный кабинет
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        mainPage.clickAccountButton();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Профиль не загрузился", profilePage.isProfileHeaderVisible());

        // Клик по кнопке "Выйти"
        profilePage.clickLogoutButton();

        // Проверка перехода на страницу логина
        assertTrue("Форма входа не отображается после выхода", loginPage.isLoginFormVisible());
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driverRule.getDriver() != null) {
            driverRule.getDriver().quit();
        }
    }
}
