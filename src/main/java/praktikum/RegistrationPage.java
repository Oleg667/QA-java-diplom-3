package praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;

    private By nameInput = By.name("name");
    private By emailInput = By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input");
    private By passwordInput = By.name("Пароль");
    private By registerButton = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");
    private By loginHeader = By.xpath("//h2[text()='Вход']");
    private By passwordError = By.xpath("//*[text()='Некорректный пароль']");
    private final By modalOverlay = By.className("Modal_modal_overlay__x2ZCr");

    private By loginLink = By.linkText("Войти");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы регистрации")
    public RegistrationPage openRegistrationPage() {
        driver.get(EnvConfig.REGISTRATION_URL);
        return this;
    }

    @Step("Ввод имени: {name}")
    public void enterName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    @Step("Проверка перехода на страницу входа")
    public boolean isLoginPageVisible() {
        return driver.findElement(loginHeader).isDisplayed();
    }

    @Step("Проверка ошибки 'Некорректный пароль'")
    public boolean isPasswordErrorVisible() {
        return driver.findElement(passwordError).isDisplayed();
    }

    @Step("Заполнение всех полей для регистрации")
    public void register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegister();
    }

    @Step("Клик на кнопку 'Регистрация'")
    public void clickLoginFromRegistration() {
        driver.findElement(loginLink).click();
    }

    @Step("Ожидание исчезновения Overlay,15 секунд ждем")
    public void waitForModalToDisappear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalOverlay));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (TimeoutException e) {
        }
    }
}