package praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {
    private WebDriver driver;
    private By loginLink = By.linkText("Войти");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик на ссылку 'Войти' в форме восстановления пароля")
    public void clickLoginFromForgotPassword() {
        driver.findElement(loginLink).click();
    }
}
