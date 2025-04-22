package homework3;

import homework.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    public HomePage(){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[text()='Candidate']")
    WebElement candidateTab;

    @FindBy(xpath = "//a[@href='/register']")
    WebElement registerButton;

    @Step("hover on candidate Tab")
    public HomePage hoverOnCandidateTab() throws InterruptedException {
        action.moveToElement(candidateTab).perform();
        return this;
    }

    public StaffRegisterPage clickOnRegisterButton(){
        registerButton.click();
        return new StaffRegisterPage();
    }

}