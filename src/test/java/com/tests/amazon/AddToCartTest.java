package com.tests.amazon;

import com.example.amazon.pages.HomePage;
import com.example.amazon.pages.ProductPage;
import com.example.amazon.pages.SearchResultPage;
import com.zebrunner.carina.core.AbstractTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends AbstractTest {
    @Test
    public void testAddToCart() {
        HomePage home = new HomePage(getDriver());
        home.open();
        home.tryDifferentCaptchaImageIfPresent();
        home.dismissCountryPopup();
        SearchResultPage results = home.search("Cuddly White Alpaca Llama Lamb Toy -7");
        Assert.assertTrue(results.isAddToCartPresent(), "Expected Add to Cart button on results");

        ProductPage product = results.clickFirstResult();
        Assert.assertTrue(product.getProductTitle().toLowerCase().contains("alpaca"), "Title must mention alpaca");
        product.clickAddToCart();
        Assert.assertTrue(product.isAddedToCartConfirmationVisible(), "Confirmation message must display");

        String cartCount = getDriver().findElement(By.id("nav-cart-count")).getText();
        Assert.assertEquals(cartCount, "1");
    }

}