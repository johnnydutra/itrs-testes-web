package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import base.BaseTests;
import org.junit.jupiter.api.Test;
import pages.*;
import util.Functions;

import java.util.List;

public class HomePageTests extends BaseTests {

    ProductPage productPage;
    LoginPage loginPage;
    ProductModalPage productModalPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    ConfirmationPage confirmationPage;

    @Test
    public void testCountProducts_EightDifferentProducts() {
        loadHomePage();
        assertThat(homePage.countProducts(), is(8));
    }

    @Test
    public void testValidateEmptyCart_ZeroItemsInCart() {
        int itemsInCart = homePage.getItemsInCartQuantity();
        assertThat(itemsInCart, is(0));
    }

    String productNameOnProductPage;
    String productPriceOnProductPage;

    @Test
    public void testValidateProductDetails_MatchingDescriptionAndPrice() {
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
    public void testSuccessfulLogin_UserLogged() {
        loginPage = homePage.clickSignInButton();
        loginPage.fillEmail("testy.mctester154@gmail.com");
        loginPage.fillPassword("abcd1234");
        loginPage.clickSignInButton();
        assertThat(homePage.isUserLogged("Testy McTester"), is(true));
        loadHomePage();
    }

    @Test
    public void testAddProductToCart_ProductAddedToCart() {
        String productSize = "M";
        String productColor = "Black";
        int productQty = 2;

        if (!homePage.isUserLogged("Testy McTester")) {
            testSuccessfulLogin_UserLogged();
        }
        testValidateProductDetails_MatchingDescriptionAndPrice();

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
    public void testGoToCart_PersistedOrderData() {
        testAddProductToCart_ProductAddedToCart();
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

    String expected_customerName = "Testy McTester";
    Double expected_shippingCostCarrier = 7.00;

    @Test
    public void testGoToCheckout_ShippingPaymentAddressListed() {
        testGoToCart_PersistedOrderData();

        checkoutPage = cartPage.clickProceedToCheckoutButton();

        assertThat(Functions.removeCurrencySignAndCastToDouble(checkoutPage.getTotalPriceAfterTax()), is(expected_afterTaxTotal));
        assertTrue(checkoutPage.getCustomerName().startsWith(expected_customerName));

        checkoutPage.clickContinueAddressButton();

        String actual_shippingCostCarrierText = checkoutPage.getShippingCostCarrier();
        actual_shippingCostCarrierText = Functions.removeSubstring(actual_shippingCostCarrierText, " tax excl.");
        Double actual_shippingCostCarrier = Functions.removeCurrencySignAndCastToDouble(actual_shippingCostCarrierText);

        assertThat(actual_shippingCostCarrier, is (expected_shippingCostCarrier));

        checkoutPage.clickContinueShippingButton();
        checkoutPage.selectPayByCheck();

        Double actual_payByCheckAmount = Functions.removeCurrencySignAndCastToDouble(Functions.removeSubstring(checkoutPage.getAmountPayByCheck(), " (tax incl.)"));
        assertThat(actual_payByCheckAmount, is(expected_afterTaxTotal));

        checkoutPage.switchAgreementCheckbox();
        assertTrue(checkoutPage.isAgreementBoxChecked());
    }

    @Test
    public void testCompleteOrder_OrderCompletedSuccessfully() {
        testGoToCheckout_ShippingPaymentAddressListed();
        confirmationPage = checkoutPage.clickConfirmOrderButton();

        assertTrue(confirmationPage.getConfirmationPageHeader().endsWith("YOUR ORDER IS CONFIRMED"));

        assertThat(confirmationPage.getUserEmail(), is("testy.mctester154@gmail.com"));
        assertThat(confirmationPage.getSubtotal(), is(expected_orderSubtotal));
        assertThat(confirmationPage.getOrderTotal(), is(expected_afterTaxTotal));
        assertThat(confirmationPage.getPaymentMethod(), is("Payments by check"));

    }


}
