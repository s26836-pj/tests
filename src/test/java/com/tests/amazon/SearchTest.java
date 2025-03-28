package com.tests.amazon;

import com.example.amazon.pages.HomePage;
import com.example.amazon.pages.SearchResultPage;
import com.zebrunner.carina.core.AbstractTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SearchTest extends AbstractTest {
    @Test
    public void testSearchResultDisplayed() {
        HomePage home = new HomePage(getDriver());
        home.open();
        home.tryDifferentCaptchaImageIfPresent();
        home.dismissCountryPopup();
        SearchResultPage results = home.search("Cuddly White Alpaca Llama Lamb Toy -7");
        List<String> titles = results.getAllProductTitles();
        boolean found = titles.stream()
                .anyMatch(title -> {
                    String t = title.toLowerCase();
                    return t.contains("alpaca") || t.contains("llama");
                });
        Assert.assertTrue(found, "no llama");

    }
}