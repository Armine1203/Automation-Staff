package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    @FindBy(xpath = "(//div[text()='Companies'])[2]")
    private WebElement companiesTab;

    @FindBy(xpath = "//div[@class='ant-select-selector']//input[@type='search']" )
    private WebElement allIndustriesSearchField;

    @FindBy(xpath = "//img[@alt='search-icon']")
    private WebElement searchIcon;

    public MainPage clickCompaniesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(companiesTab)).click();
        return this;
    }

    public CompaniesPage searchIndustry() {
        wait.until(ExpectedConditions.elementToBeClickable(allIndustriesSearchField));
        allIndustriesSearchField.click();

        wait.until(ExpectedConditions.visibilityOf(allIndustriesSearchField));

        allIndustriesSearchField.sendKeys("Information technologies");
        allIndustriesSearchField.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
        searchIcon.click();
        return new CompaniesPage();
    }
}