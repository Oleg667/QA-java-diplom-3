package praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private final WebDriverWait wait;
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By accountButton = By.xpath("//p[text()='Личный Кабинет']");
    private By orderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By constructorLink = By.xpath("//p[text()='Конструктор']");
    private final By logoButton = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");
    private final By bunsTab = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']/parent::div");
    protected By modalOverlay = By.className("Modal_modal_overlay__x2ZCr");

    // Локаторы активных вкладок
    private final By activeBunsTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span[text()='Булки']");
    private final By activeSaucesTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span[text()='Соусы']");
    private final By activeFillingsTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span[text()='Начинки']");


    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Клик на кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Клик на кнопку 'Личный кабинет'")
    public void clickAccountButton() {
        try {
            // Ждём, пока модалка появится (если появится)
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalOverlay));
            // Затем — пока исчезнет
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (TimeoutException e) {
            // если модалка вообще не появилась — ок, продолжаем
        }
        wait.until(ExpectedConditions.elementToBeClickable(accountButton)).click();
    }

    @Step("Проверка отображения в интерфейсе кнопки 'Оформить заказ'")
    public boolean isOrderButtonVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton));
            return button.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Клик на раздел 'Конструктор'")
    public void clickConstructorLink() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalOverlay));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (TimeoutException e) {
        }
        wait.until(ExpectedConditions.elementToBeClickable(constructorLink)).click();
    }

    @Step("Клик на логотип 'Stellar Burgers'")
    public void clickLogo() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalOverlay));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (TimeoutException e) {
        }
        wait.until(ExpectedConditions.elementToBeClickable(logoButton)).click();
    }

    @Step("Клик на вкладку 'Булки'")
    public void clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
    }

    @Step("Клик на вкладку 'Соусы'")
    public void clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
    }

    @Step("Клик на вкладку 'Начинки'")
    public void clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
    }

    // Методы для получения активных вкладок
    @Step("Получить активную вкладку 'Булки'")
    public WebElement getActiveBunsTab() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeBunsTab));
    }

    @Step("Получить активную вкладку 'Соусы'")
    public WebElement getActiveSaucesTab() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeSaucesTab));
    }

    @Step("Получить активную вкладку 'Начинки'")
    public WebElement getActiveFillingsTab() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeFillingsTab));
    }

}
