package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import base.BaseTests;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductModalPage;
import pages.ProductPage;
import util.Functions;

import java.util.List;

public class HomePageTests extends BaseTests {

    ProductPage productPage;
    LoginPage loginPage;
    ProductModalPage productModalPage;
    CartPage cartPage;

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

    String productNameOnProductPage;
    String productPriceOnProductPage;

    @Test
    public void testValidateProductDetails_matchingDescriptionAndPrice() {
        int index = 0;
        String productNameOnHomePage = homePage.getProductName(index);
        String productPriceOnHomePage = homePage.getProductPrice(index);
        productPage = homePage.clickProduct(index);

        productNameOnProductPage = productPage.getProductName();
        productPriceOnProductPage = productPage.getProductPrice();

        assertThat(productNameOnHomePage.toUpperCase(), is(productNameOnProductPage.toUpperCase()));
        assertThat(productPriceOnHomePage, is(productPriceOnProductPage));

    }

    @Test
    public void testSuccessfulLogin_userLogged() {
        loginPage = homePage.clickSignInButton();
        loginPage.fillEmail("testy.mctester154@gmail.com");
        loginPage.fillPassword("abcd1234");
        loginPage.clickSignInButton();
        assertThat(homePage.isUserLogged("Testy McTester"), is(true));
        loadHomePage();
    }

    @Test
    public void addProductToCart_ProductAddedToCart() {
        String productSize = "M";
        String productColor = "Black";
        int productQty = 2;

        if (!homePage.isUserLogged("Testy McTester")) {
            testSuccessfulLogin_userLogged();
        }
        testValidateProductDetails_matchingDescriptionAndPrice();

        List<String> sizeList = productPage.getSelectedSize();
        System.out.println(sizeList.get(0));
        productPage.selectSizeDropdownOption(productSize);

        sizeList = productPage.getSelectedSize();
        System.out.println(sizeList.get(0));

        productPage.selectProductColorBlack();

        productPage.setProductQty(productQty);

        productModalPage =  productPage.clickAddToCartButton();
        assertTrue(productModalPage.getModalHeaderText().endsWith("Product successfully added to your shopping cart"));

        assertThat(productModalPage.getProductName().toUpperCase(), is(productNameOnProductPage.toUpperCase()));

        String productPriceString = productModalPage.getProductPrice();
        productPriceString = productPriceString.replace("$", "");
        Double productPrice = Double.parseDouble(productPriceString);

//        assertThat(productModalPage.getProductSize(), is(productSize));
//        assertThat(productModalPage.getProductColor(), is(productColor));
//        assertThat(productModalPage.getProductQty(), is(productQty));

        String subtotalString = productModalPage.getSubtotal();
        subtotalString = subtotalString.replace("$", "");
        Double subtotal = Double.parseDouble(subtotalString);

        Double subtotalCalc = productPrice * productQty;
        assertThat(subtotal, is(subtotalCalc));

    }

    String expected_productName = "Hummingbird printed t-shirt";
    Double expected_productPrice = 19.12;
    String expected_productSize = "M";
    String expected_productColor = "Black";
    int expected_productQty = 2;
    Double expected_productSubtotal = expected_productPrice * expected_productQty;

    int expected_orderItemsTotal = expected_productQty;
    Double expected_orderSubtotal = expected_productSubtotal;
    Double expected_orderShippingValue = 7.00;
    Double expected_beforeTaxTotal = expected_productSubtotal + expected_orderShippingValue;
    Double expected_taxes = 0.00;
    Double expected_afterTaxTotal = expected_beforeTaxTotal + expected_taxes;

    @Test
    public void goToCheckout_PersistedOrderData() {
        addProductToCart_ProductAddedToCart();
        cartPage = productModalPage.clickProceedToCheckout();

        assertThat(cartPage.getProductName(), is(expected_productName));
        assertThat(Functions.removeCurrencySignAndCastToDouble(cartPage.getProductPrice()), is(expected_productPrice));
        assertThat(cartPage.getProductSize(), is(expected_productSize));
        assertThat(cartPage.getProductColor(), is(expected_productColor));
        assertThat(cartPage.getProductQty(), is(expected_productQty));
        assertThat(Functions.removeCurrencySignAndCastToDouble(cartPage.getProductSubtotal()), is(expected_productSubtotal));
        assertThat(Functions.removeItemsStringAndCastToInt(cartPage.getOrderItemsTotalText()), is(expected_orderItemsTotal));
        assertThat(Functions.removeCurrencySignAndCastToDouble(cartPage.getOrderSubtotalText()), is(expected_orderSubtotal));
        assertThat(Functions.removeCurrencySignAndCastToDouble(cartPage.getOrderShippingValueText()), is(expected_orderShippingValue));
        assertThat(Functions.removeCurrencySignAndCastToDouble(cartPage.getBeforeTaxTotalText()), is(expected_beforeTaxTotal));
        assertThat(Functions.removeCurrencySignAndCastToDouble(cartPage.getAfterTaxTotalText()), is(expected_afterTaxTotal));
        assertThat(Functions.removeCurrencySignAndCastToDouble(cartPage.getTaxesText()), is(expected_taxes));



    }



}
