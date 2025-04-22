package homework3;

import homework.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ScreenshotUtil;

public class DropdownComponent extends BasePage {
    private final String baseXPath;
    private static final String DROPDOWN_LABEL_XPATH = "%s//following-sibling::span/div[text()='%s']";
    private static final String OPTION_XPATH = "//div[text()='%s']";

    public DropdownComponent(String baseXPath) {
        this.baseXPath = baseXPath;
    }

    public void selectOption(String dropdownLabel, String optionToSelect) {
        clickDropdown(dropdownLabel);
        selectOptionFromList(optionToSelect);
    }

    private void clickDropdown(String dropdownLabel) {
        By dropdownLocator = By.xpath(String.format(DROPDOWN_LABEL_XPATH, baseXPath, dropdownLabel));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        action.moveToElement(dropdown).click().perform();
    }

    private void selectOptionFromList(String optionToSelect) {
        try{By optionLocator = By.xpath(String.format(OPTION_XPATH, optionToSelect));

        for (int attempt = 0; attempt < 90; attempt++) {
            try {
                WebElement option = driver.findElement(optionLocator);
                scrollAndClick(option);
                return;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                action.sendKeys(Keys.ARROW_DOWN).perform();
            }
        }
        handleOptionNotFound(optionToSelect);
    }catch (RuntimeException e ){
            logger.error("Option not found");
        }
    }

    private void scrollAndClick(WebElement option) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", option);
        option.click();
    }

    private void handleOptionNotFound(String optionToSelect) {
        ScreenshotUtil.attachScreenshot(driver, "Dropdown option not found");
        throw new RuntimeException();
    }
}