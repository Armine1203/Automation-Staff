package homework3;

import homework.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ScreenshotUtil;

public class StaffRegisterPage extends BasePage {
    DropdownComponent dropdownComponent = new DropdownComponent("//span[input[@type='search']]");

    public StaffRegisterPage() {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//div[text()='First name']//following-sibling::div/input")
    public WebElement firstNameInput;

    @FindBy(xpath = "//div[text()='Last name']//following-sibling::div/input")
    public WebElement lastNameInput;

    @FindBy(xpath = "//div[text()='Email']//following-sibling::div/input")
    public WebElement emailInput;

    @FindBy(xpath = "//div[text()='The field must be a valid email address.']")
    WebElement invalidEmailMessage;

    @FindBy(xpath = "//div[text()='Password']//following-sibling::div/input")
    public WebElement passwordInput;

    @FindBy(xpath = "//div[text()='Confirm password']//following-sibling::div/input")
    public WebElement confirmPasswordInput;

    @FindBy(xpath = "//span[contains(text(),\"I agree to staff.am’s \")]")
    public WebElement privacyPolicyCheckbox;

    @FindBy(xpath = "(//div[text()='Register'])[3]")
    private WebElement formRegisterButton;

    @Step("fill in first name field")
    public StaffRegisterPage fillFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.click();
        firstNameInput.sendKeys(firstName);
        logger.info("first name is filled in");
        return this;
    }

    @Step("fill in last name field")
    public StaffRegisterPage fillLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameInput));
        lastNameInput.click();
        lastNameInput.sendKeys(lastName);
        logger.info("last name is filled in");
        return this;
    }

    @Step("fill in email field")
    public StaffRegisterPage fillEmail(boolean isValid,String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.click();
        emailInput.sendKeys(email);
        if (isValid){
            wait.until(ExpectedConditions.invisibilityOf(invalidEmailMessage));
            logger.info("invalid message is missed");
            ScreenshotUtil.attachScreenshot(driver,"invalid message is missed");

        }else {
            wait.until(ExpectedConditions.textToBePresentInElement(invalidEmailMessage,"The field must be a valid email address."));
            logger.info("invalid message is available and match to text");
            ScreenshotUtil.attachScreenshot(driver,"invalid message is available");


        }

        logger.info("email is filled");

        return this;
    }

    @Step("fill in password field")
    public StaffRegisterPage fillPassword(String password){
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.click();
        passwordInput.sendKeys(password);
        logger.info("'Password' is filled in");
        return this;
    }

    @Step("fill in confirm password field")
    public StaffRegisterPage fillConfirmPassword(String password){
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        confirmPasswordInput.click();
        confirmPasswordInput.sendKeys(password);
        logger.info("'Confirm password' is filled in");
        return this;
    }

    public StaffRegisterPage selectPrivacyPolicyCheckbox(){
        wait.until(ExpectedConditions.visibilityOf(privacyPolicyCheckbox));
        privacyPolicyCheckbox.click();
        logger.info("Privacy policy checkbox is selected");
        return this;
    }

    @Step("Fill in birthday dropdowns: year, month, day")
    public void fillBirthdayDropDown(String year, String month, String day) {
        dropdownComponent.selectOption("Year", year);
        dropdownComponent.selectOption("Month", month);
        dropdownComponent.selectOption("Day", day);
        ScreenshotUtil.attachScreenshot(driver);
    }

    public String getRegisterButtonCurrentStyle(){
        return wait.until(ExpectedConditions.visibilityOf(formRegisterButton)).getDomAttribute("style");
    }


}