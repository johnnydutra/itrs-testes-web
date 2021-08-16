package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    List<WebElement> productList = new ArrayList<>();

    private By products = By.className("product-description");
    private By cartItemsText = By.className("cart-products-count");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public int countProducts() {
        loadProductList();
        return productList.size();
    }

    private void loadProductList() {
        productList = driver.findElements(products);
    }

    public int getItemsInCartQuantity() {
        String cartItemsQtyText = driver.findElement(cartItemsText).getText();
        cartItemsQtyText = cartItemsQtyText.replace("(","");
        cartItemsQtyText = cartItemsQtyText.replace(")","");
        int cartItemsQtyNumber = Integer.parseInt(cartItemsQtyText);
        return cartItemsQtyNumber;
    }


}
