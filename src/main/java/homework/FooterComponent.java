package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FooterComponent extends BasePage {
    @FindBy(xpath = "//div[text()='Companies']/parent::div//div[text()='View all companies']")
    WebElement companiesViewAllCompaniesTab;

    public ResultPage clickCompaniesViewAllCompaniesTab() {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", companiesViewAllCompaniesTab);
        wait.until(ExpectedConditions.elementToBeClickable(companiesViewAllCompaniesTab));
        companiesViewAllCompaniesTab.click();
        return new ResultPage();
    }

}
