package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;

    private By productName = By.cssSelector("div.product-line-info a");
    private By productPrice = By.cssSelector("span.price");
    private By productSize = By.xpath("//div[contains(@class, 'product-line-grid-body')]//div[3]/span[@class='value']");
    private By productColor = By.xpath("//div[contains(@class, 'product-line-grid-body')]//div[4]/span[@class='value']");
    private By productQty = By.cssSelector("input.js-cart-line-product-quantity");
    private By productSubtotal = By.cssSelector("span.product-price strong");

    private By orderItemsTotal = By.cssSelector("span.js-subtotal");
    private By orderSubtotal = By.cssSelector("#cart-subtotal-products span.value");
    private By orderShippingValue = By.cssSelector("#cart-subtotal-shipping span.value");
    private By beforeTaxTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(1) span.value");
    private By afterTaxTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(2) span.value");
    private By taxes = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(3) span.value");

    private By proceedToCheckoutButton = By.cssSelector("a.btn-primary");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public String getProductSize() {
        return driver.findElement(productSize).getText();
    }

    public String getProductColor() {
        return driver.findElement(productColor).getText();
    }

    public int getProductQty() {
        return Integer.parseInt(driver.findElement(productQty).getAttribute("value"));
    }

    public String getProductSubtotal() {
        return driver.findElement(productSubtotal).getText();
    }

    public String getOrderItemsTotalText() {
        return driver.findElement(orderItemsTotal).getText();
    }

    public String getOrderSubtotalText() {
        return driver.findElement(orderSubtotal).getText();
    }

    public String getOrderShippingValueText() {
        return driver.findElement(orderShippingValue).getText();
    }

    public String getBeforeTaxTotalText() {
        return driver.findElement(beforeTaxTotal).getText();
    }

    public String getAfterTaxTotalText() {
        return driver.findElement(afterTaxTotal).getText();
    }

    public String getTaxesText() {
        return driver.findElement(taxes).getText();
    }

    public CheckoutPage clickProceedToCheckoutButton() {
        driver.findElement(proceedToCheckoutButton).click();
        return new CheckoutPage(driver);
    }



}
