package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FooterComponent extends BasePage{
    By selector_companiesViewAllCompaniesTab = By.xpath("//div[text()='Companies']/parent::div//div[text()='View all companies']");

    public ResultPage clickCompaniesViewAllCompaniesTab() throws InterruptedException {
        WebElement companiesViewAllCompaniesTab = wait.until(ExpectedConditions.elementToBeClickable(selector_companiesViewAllCompaniesTab));
//        javascriptExecutor.executeScript("window.scrollBy(0,250)", companiesViewAllCompaniesTab);
//        action.moveToElement(companiesViewAllCompaniesTab).perform();//change to JS for scroll
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", companiesViewAllCompaniesTab);

        Thread.sleep(5000);
        companiesViewAllCompaniesTab.click();
        return new ResultPage();
    }

}
