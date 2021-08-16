package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import base.BaseTests;
import org.junit.jupiter.api.Test;
import pages.ProductPage;

public class HomePageTests extends BaseTests {

    @Test
    public void testCountProducts_eightDifferentProducts() {
        loadHomePage();
        assertThat(homePage.countProducts(), is(8));
    }

    @Test
    public void testValidateEmptyCart_zeroItemsInCart() {
        int itemsInCart = homePage.getItemsInCartQuantity();
        assertThat(itemsInCart, is(0));
    }

    @Test
    public void testValidateProductDetails_matchingDescriptionAndPrice() {
        int index = 0;
        String productNameOnHomePage = homePage.getProductName(index);
        String productPriceOnHomePage = homePage.getProductPrice(index);
        ProductPage productPage = homePage.clickProduct(index);

        String productNameOnProductPage = productPage.getProductName();
        String productPriceOnProductPage = productPage.getProductPrice();

        assertThat(productNameOnHomePage.toUpperCase(), is(productNameOnProductPage.toUpperCase()));
        assertThat(productPriceOnHomePage, is(productPriceOnProductPage));

    }

}
