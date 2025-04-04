package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FooterComponent extends BasePage{
    By selector_companiesViewAllCompaniesTab = By.xpath("//div[text()='Companies']/parent::div//div[text()='View all companies']");
    WebElement companiesViewAllCompaniesTab = driver.findElement(selector_companiesViewAllCompaniesTab);
    public ResultPage clickCompaniesViewAllCompaniesTab() throws InterruptedException {
        action.moveToElement(companiesViewAllCompaniesTab).perform();
        Thread.sleep(3000);
        companiesViewAllCompaniesTab.click();
        return new ResultPage();
    }

}
