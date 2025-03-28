package com.example.amazon.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultPage extends AbstractPage {

    @FindBy(css = "div[data-component-type='s-search-result'] button")
    private List<ExtendedWebElement> resultButtons;

    @FindBy(css = "div[data-component-type='s-search-result'] h2 a")
    private List<ExtendedWebElement> resultLinks;

    public ProductPage clickFirstResult() {
        if (resultLinks.isEmpty()) {
            throw new RuntimeException("No search results found");
        }
        resultLinks.get(0).click();
        return new ProductPage(getDriver());
    }


    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddToCartPresent() {
        return resultButtons.stream()
                .map(ExtendedWebElement::getText)
                .anyMatch(text -> text.trim().equalsIgnoreCase("Add to Cart"));
    }

    public void clickAddToCart() {
        ExtendedWebElement btn = resultButtons.stream()
                .filter(b -> b.getText().trim().equalsIgnoreCase("Add to Cart"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Add to Cart button found"));
        btn.click();
    }

    public List<String> getAllProductTitles() {
        return resultLinks.stream()
                .map(ExtendedWebElement::getText)
                .collect(Collectors.toList());
    }

}