package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    By emailInput = By.name("email");
    By passwordInput = By.name("password");
    By signInButton = By.id("submit-login");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void fillPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

}
