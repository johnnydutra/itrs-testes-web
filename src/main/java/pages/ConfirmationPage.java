package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.Functions;

public class ConfirmationPage {

    WebDriver driver;

    By confirmationPageHeader = By.cssSelector("#content-hook_order_confirmation h3");
    By userEmail = By.cssSelector("#content-hook_order_confirmation p");
    By subtotal = By.cssSelector("div.order-confirmation-table div.order-line div.row div.bold");
    By orderTotal = By.cssSelector(".order-confirmation-table table tr.total-value td:nth-child(2)");
    By paymentMethod = By.cssSelector("#order-details ul li:nth-child(2)");


    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getConfirmationPageHeader() {
        return driver.findElement(confirmationPageHeader).getText();
    }

    public String getUserEmail() {
        String text = Functions.removeSubstring(driver.findElement(userEmail).getText(), "An email has been sent to the ");
        return Functions.removeSubstring(text, " address.");
    }

    public Double getSubtotal() {
        return Functions.removeCurrencySignAndCastToDouble(driver.findElement(subtotal).getText());
    }

    public Double getOrderTotal() {
        return Functions.removeCurrencySignAndCastToDouble(driver.findElement(orderTotal).getText());
    }

    public String getPaymentMethod() {
        return Functions.removeSubstring(driver.findElement(paymentMethod).getText(), "Payment method: ");
    }


}
