package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class ProductModalPage {

    private WebDriver driver;

    private By modalHeader = By.id("myModalLabel");
    private By productName = By.className("product-name");
    private By productPrice = By.cssSelector("div.modal-body p.product-price");
    private By productAttributes = By.cssSelector("div.divide-right .col-md-6:nth-child(2) span strong");
    private By subtotal = By.xpath("//div[@class='cart-content']//p[2]/span[@class='value']");
    private By proceedToCheckoutButton = By.cssSelector("div.cart-content-btn a.btn-primary");

    public ProductModalPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getModalHeaderText() {
        FluentWait wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalHeader));
        return driver.findElement(modalHeader).getText();
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public String getProductSize() {
        return driver.findElements(productAttributes).get(0).getText();
    }

    public String getProductColor() {
        return driver.findElements(productAttributes).get(1).getText();
    }

    public String getProductQty() {
        return driver.findElements(productAttributes).get(2).getText();
    }

    public String getSubtotal() {
        return driver.findElement(subtotal).getText();
    }

    public CartPage clickProceedToCheckout() {
        driver.findElement(proceedToCheckoutButton).click();
        return new CartPage(driver);
    }

}
