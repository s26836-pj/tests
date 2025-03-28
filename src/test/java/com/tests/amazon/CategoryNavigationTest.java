package com.tests.amazon;

import com.example.amazon.pages.HomePage;
import com.zebrunner.carina.core.AbstractTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CategoryNavigationTest extends AbstractTest {
    @Test
    public void testNavigateToToysCategory() {
        HomePage home = new HomePage(getDriver());
        home.open();
        home.tryDifferentCaptchaImageIfPresent();
        home.dismissCountryPopup();

        home.openAllMenu();
        home.selectCategory("Electronics");
        home.selectSubCategory("Headphones");


        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.toLowerCase().contains("headphones"), "Not working :)");
    }
}