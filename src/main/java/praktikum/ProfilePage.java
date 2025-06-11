package praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;
    private final WebDriverWait wait;

    private By profileHeader = By.xpath("//*[@id='root']/div/main/div/nav/p");
    private By logoutButton = By.xpath("//button[text()='Выход']");
    private final By modalOverlay = By.className("Modal_modal_overlay__x2ZCr");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Проверка наличия заголовка профиля")
    public boolean isProfileHeaderVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(profileHeader)).isDisplayed();
    }

    @Step("Проверка наличия кнопки выхода")
    public boolean isLogoutButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).isDisplayed();
    }

    @Step("Клик на кнопку 'Выход'")
    public void clickLogoutButton() {
        // Ждём, пока исчезнет модальное окно
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalOverlay));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (TimeoutException ignored) {
            // если модалка не появилась — норм
        }
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }
}
