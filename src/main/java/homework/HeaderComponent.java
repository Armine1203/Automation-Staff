package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderComponent extends BasePage {
    @FindBy (xpath = "//div[text()='Companies']//parent::a")
    WebElement companyTab;

    public CompaniesPage clickOnCompaniesTab(){
        wait.until(ExpectedConditions.visibilityOf(companyTab));
        companyTab.click();
        return new CompaniesPage();
    }

}
