
package com.example.amazon.pages;

import com.example.amazon.components.HeaderComponent;
import com.zebrunner.carina.utils.common.CommonUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

public class HomePage extends AbstractPage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    @FindBy(id = "nav-search-bar-form")
    private HeaderComponent header;

    @FindBy(xpath = "//span[normalize-space()='Dismiss']")
    private ExtendedWebElement dismissPopup;

    @FindBy(id = "nav-hamburger-menu")
    private ExtendedWebElement hamburgerMenu;

    @FindBy(css = "h1.a-size-large")
    private ExtendedWebElement categoryHeader;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageURL("/");
    }

    public void openAllMenu() {
        hamburgerMenu.click();
    }

    public void selectCategory(String name) {
        By categoryLocator = By.xpath("//*[@id=\"hmenu-content\"]/ul[1]/li[8]/a");


        findExtendedWebElement(categoryLocator).click(5);


        CommonUtils.pause(1000);
    }

    public void selectSubCategory(String name) {
        By linkLocator = By.xpath("//a[normalize-space(text())='" + name + "']");
        ExtendedWebElement subCat = findExtendedWebElement(linkLocator);

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", subCat.getElement());
        CommonUtils.pause(300);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", subCat.getElement());
    }





    public void tryDifferentCaptchaImageIfPresent() {
        List<ExtendedWebElement> elements = findExtendedWebElements(By.xpath("//a[text()='Try different image']"));

        if (!elements.isEmpty() && elements.get(0).isElementPresent(1)) {
            LOGGER.info("CAPTCHA detected. Clicking 'Try different image'");
            elements.get(0).click(5);
        }
    }



    public String getCategoryHeader() {
        return categoryHeader.getText();
    }

    public void dismissCountryPopup() {
        List<ExtendedWebElement> buttons = findExtendedWebElements(By.xpath("//span[normalize-space()='Dismiss']"));
        buttons.stream()
                .filter(ExtendedWebElement::isElementPresent)
                .findFirst()
                .ifPresent(b -> {
                    b.click(5);
                    LOGGER.info("Country‑selector popup dismissed");
                });
    }


    public SearchResultPage search(String query) {
        header.search(query);
        return new SearchResultPage(driver);
    }
}
