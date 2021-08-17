package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    WebDriver driver;

    By totalPriceAfterTax = By.cssSelector(".cart-total span.value");
    By customerName = By. cssSelector("div.address");
    By continueAddressButton = By.name("confirm-addresses");
    By shippingCostCarrier = By.cssSelector("span.carrier-price");
    By continueShippingButton = By.name("confirmDeliveryOption");
    By radioPayByCheck = By.id("payment-option-1");
    By amountPayByCheck = By.cssSelector("#payment-option-1-additional-information > section > dl > dd:nth-child(2)");
    By agreeTermsCheckbox = By.cssSelector(".custom-checkbox input");
    By confirmOrderButton = By.cssSelector("#payment-confirmation button");


    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPriceAfterTax() {
        return driver.findElement(totalPriceAfterTax).getText();
    }

    public String getCustomerName() {
        return driver.findElement(customerName).getText();
    }

    public void clickContinueAddressButton() {
        driver.findElement(continueAddressButton).click();
    }

    public String getShippingCostCarrier() {
        return driver.findElement(shippingCostCarrier).getText();
    }

    public void clickContinueShippingButton() {
        driver.findElement(continueShippingButton).click();
    }

    public void selectPayByCheck() {
        driver.findElement(radioPayByCheck).click();
    }

    public String getAmountPayByCheck() {
        return driver.findElement(amountPayByCheck).getText();
    }

    public void switchAgreementCheckbox() {
        driver.findElement(agreeTermsCheckbox).click();
    }

    public boolean isAgreementBoxChecked() {
        return driver.findElement(agreeTermsCheckbox).isSelected();
    }

    public ConfirmationPage clickConfirmOrderButton() {
        driver.findElement(confirmOrderButton).click();
        return new ConfirmationPage(driver);
    }


}
