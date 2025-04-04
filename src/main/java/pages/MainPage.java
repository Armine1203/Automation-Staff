package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {
    private final By selectorCompanies = By.xpath("(//div[text()='Companies'])[2]");
    private final By selectorSearchInput = By.xpath("//div[@class='ant-select-selector']//input[@type='search']");
    private final By selectorSearchIcon = By.xpath("//img[@alt='search-icon']");//search icon which I don't use, because Industry dropdown doesn't work



    public MainPage clickCompaniesTab() {
        driver.findElement(selectorCompanies).click();
        return this;
    }

    public CompaniesPage searchIndustry() throws InterruptedException {
        WebElement allIndustriesSearchField = driver.findElement(selectorSearchInput);
        allIndustriesSearchField.click();
        Thread.sleep(5000);

        allIndustriesSearchField.sendKeys("Information technologies");
        allIndustriesSearchField.sendKeys(Keys.ENTER);

        WebElement searchIcon = driver.findElement(selectorSearchIcon);
        Thread.sleep(3000);
        searchIcon.click();
        return new CompaniesPage();
    }
}
