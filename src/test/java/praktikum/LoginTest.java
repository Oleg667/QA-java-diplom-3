package praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private final String email = "vladtest@yandex.rus";
    private final String password = "11111111";

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    @Step("Инициализация и открытие браузера")
    public void setUp() {
        WebDriver driver = driverRule.getDriver();
        driver.get(EnvConfig.BASE_URL);
    }

    @Test
    @DisplayName("Проверка входа через кнопку «Войти в аккаунт» на главной")
    public void loginFromMainPage() {
        WebDriver driver = driverRule.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        assertTrue("Кнопка 'Оформить заказ' не отображается", mainPage.isOrderButtonVisible());

    }

    @Test
    @DisplayName("Проверка входа через кнопку «Личный кабинет»")
    public void loginFromAccountButton() {
        WebDriver driver = driverRule.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        assertTrue("Кнопка 'Оформить заказ' не отображается", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку в форме регистрации")
    public void loginFromRegistrationForm() {
        WebDriver driver = driverRule.getDriver();
        driver.get(EnvConfig.REGISTRATION_URL);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.waitForModalToDisappear();
        registrationPage.clickLoginFromRegistration();

        MainPage mainPage = new MainPage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        assertTrue("Кнопка 'Оформить заказ' не отображается", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку в форме восстановления пароля")
    public void loginFromForgotPasswordForm() {
        WebDriver driver = driverRule.getDriver();
        driver.get(EnvConfig.BASE_URL + "forgot-password");
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLoginFromForgotPassword();

        MainPage mainPage = new MainPage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        assertTrue("Кнопка 'Оформить заказ' не отображается", mainPage.isOrderButtonVisible());
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driverRule.getDriver() != null) {
            driverRule.getDriver().quit();
        }
    }
}
