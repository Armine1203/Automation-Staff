package pages;

import org.openqa.selenium.By;

public class HeaderComponent extends BasePage {
    By selectorCompanyTab = By.xpath("//div[text()='Companies']//parent::a");

    public CompaniesPage clickOnCompaniesTab(){
        driver.findElement(selectorCompanyTab).click();
        return new CompaniesPage();
    }

}
