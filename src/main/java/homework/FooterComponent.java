package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FooterComponent extends BasePage{
    By selector_companiesViewAllCompaniesTab = By.xpath("//div[text()='Companies']/parent::div//div[text()='View all companies']");

    public ResultPage clickCompaniesViewAllCompaniesTab(){
        WebElement companiesViewAllCompaniesTab = wait.until(ExpectedConditions.elementToBeClickable(selector_companiesViewAllCompaniesTab));
        action.moveToElement(companiesViewAllCompaniesTab).perform();//change to JS for scroll
        companiesViewAllCompaniesTab.click();
        return new ResultPage();
    }

}
