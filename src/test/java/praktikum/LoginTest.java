package praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private String email;
    private final String password = "667667667!!!";
    private String name;
    private String accessToken;
    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    @Step("Создание пользователя через API и открытие браузера")
    public void setUp() {
        // Генерация уникальных данных
        name = "usertest" + UUID.randomUUID().toString().substring(0, 6);
        email = name + "@yandex.ru";

        // Создание пользователя через API
        UserClient userClient = new UserClient();
        accessToken = userClient.createUserAndGetToken(name, email, password);
        // открытие браузера
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
    @Step("Закрытие браузера и удаление пользователя")
    public void tearDown() {
        if (driverRule.getDriver() != null) {
            driverRule.getDriver().quit();
        }
        if (accessToken != null) {
            UserClient userClient = new UserClient();
            userClient.deleteUser(accessToken);
            accessToken = null;
        }
    }

}
