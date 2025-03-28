package com.example.amazon.pages;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends AbstractPage {

    private By titleLocator        = By.id("productTitle");
   // private By priceLocator        = By.cssSelector("#priceblock_ourprice, #priceblock_dealprice");
    private By addToCartButton     = By.id("add-to-cart-button");
    private By cartConfirmationMsg = By.cssSelector("#huc-v2-order-row-confirm-text");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return getDriver().findElement(titleLocator).getText().trim();
    }


    public void clickAddToCart() {
        getDriver().findElement(addToCartButton).click();
    }

    public boolean isAddedToCartConfirmationVisible() {
        return !getDriver().findElements(cartConfirmationMsg).isEmpty();
    }
}