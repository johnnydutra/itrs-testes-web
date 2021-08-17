package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {

    private WebDriver driver;

    private By productName = By.className("h1");
    private By productPrice = By.cssSelector(".current-price span:nth-child(1)");
    private By productSize = By.id("group_1");
    private By colorSelectorBlack = By.xpath("//ul[@id='group_2']//input[@value='11']");
    private By productQty = By.id("quantity_wanted");
    private By addtoCartButton = By.className("add-to-cart");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public void selectSizeDropdownOption(String size) {
        findSizeDropdown().selectByVisibleText(size);
    }

    public List<String> getSelectedSize() {
        List<WebElement> selectedElements =  findSizeDropdown().getAllSelectedOptions();
        List<String> selectedOptionsList = new ArrayList<>();
        for (WebElement element : selectedElements) {
            selectedOptionsList.add(element.getText());
        }
        return selectedOptionsList;
    }

    public Select findSizeDropdown() {
        return new Select(driver.findElement(productSize));
    }

    public void selectProductColorBlack() {
        driver.findElement(colorSelectorBlack).click();
    }

    public void setProductQty(int qty) {
        driver.findElement(productQty).clear();
        driver.findElement(productQty).sendKeys(Integer.toString(qty));
    }

    public ProductModalPage clickAddToCartButton() {
        driver.findElement(addtoCartButton).click();
        return new ProductModalPage(driver);
    }

}
