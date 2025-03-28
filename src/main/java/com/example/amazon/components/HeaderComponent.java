package com.example.amazon.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends AbstractUIObject {

    @FindBy(id = "twotabsearchtextbox")
    private ExtendedWebElement searchInput;

    @FindBy(id = "nav-search-submit-button")
    private ExtendedWebElement searchButton;

    public HeaderComponent(WebDriver driver, SearchContext context) {
        super(driver, context);
    }

    public void search(String query) {
        searchInput.type(query);
        searchButton.click();
    }
}