package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {
    private final By selectorCompanies = By.xpath("(//div[text()='Companies'])[2]");
    private final By selectorSearchInput = By.xpath("//div[@class='ant-select-selector']//input[@type='search']");
    private final By selectorSearchIcon = By.xpath("//img[@alt='search-icon']");



    public MainPage clickCompaniesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(selectorCompanies)).click();
        return this;
    }

    public CompaniesPage searchIndustry() {
        WebElement allIndustriesSearchField = wait.until(ExpectedConditions.elementToBeClickable(selectorSearchInput));
        allIndustriesSearchField.click();

        wait.until(ExpectedConditions.visibilityOf(allIndustriesSearchField));

        allIndustriesSearchField.sendKeys("Information technologies");
        allIndustriesSearchField.sendKeys(Keys.ENTER);

        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(selectorSearchIcon));
        searchIcon.click();
        return new CompaniesPage();
    }
}
