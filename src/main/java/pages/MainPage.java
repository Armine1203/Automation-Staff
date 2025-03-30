package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private final WebDriver driver;
    private final By selectorCompanies = By.xpath("(//div[text()='Companies'])[2]");
    private final By selectorSearchInput = By.xpath("//div[@class='ant-select-selector']//input[@type='search']");
    private final By selectorSearchIcon = By.xpath("//img[@alt='search-icon']");//search icon which I don't use, because Industry dropdown doesn't work


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCompaniesTab() {
        driver.findElement(selectorCompanies).click();
    }

    public void searchIndustry() throws InterruptedException {
        WebElement allIndustriesSearchField = driver.findElement(selectorSearchInput);
        allIndustriesSearchField.click();
        Thread.sleep(5000);

        allIndustriesSearchField.sendKeys("Information technologies");
        allIndustriesSearchField.sendKeys(Keys.ENTER);

        WebElement searchIcon = driver.findElement(selectorSearchIcon);
        Thread.sleep(3000);
        searchIcon.click();
    }
}
