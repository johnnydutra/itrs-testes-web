package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import base.BaseTests;
import org.junit.jupiter.api.Test;

public class HomePageTests extends BaseTests {

    @Test
    public void countProducts_eightDifferentProducts() {
        loadHomePage();
        assertThat(homePage.countProducts(), is(8));
    }

    @Test
    public void validateEmptyCart_zeroItemsInCart() {
        int itemsInCart = homePage.getItemsInCartQuantity();
        assertThat(itemsInCart, is(0));
    }

}
