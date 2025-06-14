package praktikum;

import io.qameta.allure.junit4.DisplayName;  // Для @DisplayName
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RegistrationTest {

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    @Step("Инициализация и открытие браузера")
    public void setUp() {
        WebDriver driver = driverRule.getDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.openRegistrationPage();
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    @Description("Проверка успешной регистрации")
    public void testSuccessfulRegistration() {
        WebDriver driver = driverRule.getDriver();
        RegistrationPage registrationPage = new RegistrationPage(driver)
                .openRegistrationPage();
        registrationPage.waitForModalToDisappear();
        String email = "oleg-g" + System.currentTimeMillis() + "@yandex.ru";
        String password = "667667667";
        registrationPage.register("Oleg", email, password);
        Assert.assertTrue("Ожидалась переадресация на страницу входа", registrationPage.isLoginPageVisible());

        // удаляем пользователя
        UserClient userClient = new UserClient();
        Response loginResponse = userClient.login(email, password);

        String accessToken = loginResponse.body().jsonPath().getString("accessToken");
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Проверка ошибки при коротком пароле")
    @Description("Проверка ошибки при коротком пароле")
    public void testRegistrationWithShortPassword() {
        WebDriver driver = driverRule.getDriver();
        RegistrationPage registrationPage = new RegistrationPage(driver)
                .openRegistrationPage();
        registrationPage.waitForModalToDisappear();
        String email = generateRandomEmail();
        registrationPage.register("Короткий Пароль", email, "123");
        Assert.assertTrue("Ожидалась ошибка 'Некорректный пароль'", registrationPage.isPasswordErrorVisible());
    }

    private String generateRandomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driverRule.getDriver() != null) {
            driverRule.getDriver().quit();
        }
    }
}

