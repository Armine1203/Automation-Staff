package homework2;

import homework.BasePage;
import homework.Helpers.CurrentURL_CheckHelper;
import homework.Helpers.IntegerParseHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenshotUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobPage extends BasePage {

    public JobPage() {
        PageFactory.initElements(driver, this);
    }

    private final Random random = new Random();
    private final CurrentURL_CheckHelper helper = new CurrentURL_CheckHelper();
    private WebElement lastSelectedFilter;
    private List<WebElement> listOfSectionsCheckboxes = new ArrayList<>();
    private static List<WebElement> selectedFilters;

    private final String formattedCheckboxesXpath = "//div[text() =\"%s\"]/parent::div//div/span";
    private final String formattedViewMoreButtonXpath = "//div[text()=\"%s\"]/following-sibling::div//div[text()='View more']/parent::div";


    @FindBy (xpath = "//div[contains(text(), 'Clear filters')]")
    WebElement clearButton;

    @FindBy (xpath = "//li[@class='next']//preceding-sibling::li[1]/a")
    private WebElement paginationMaxValueElement;

    private final By selectorFilteredItems = By.xpath("//h1[text()='Current Job Openings']/parent::div/following-sibling::div//a[@role ='link']");

    @Step("Click to view more button for section: {sectionName}")
    public JobPage clickToViewMoreButton(String sectionName) {
        try {
            ScreenshotUtil.attachScreenshot(driver);
            String xpathViewMore = String.format(formattedViewMoreButtonXpath, sectionName);
            WebElement viewMore = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathViewMore)));
            if (viewMore.isDisplayed() && viewMore.isEnabled()) {
                scrollIntoView(viewMore);
                viewMore.click();
                logger.info("Clicked 'View more' button for section: {}", sectionName);
            }
        } catch (Exception e) {
            logger.error("No 'View more' button for section: {}", sectionName, e);
            ScreenshotUtil.attachScreenshot(driver,"View more button is missed");
            throw e;
        }
        return this;
    }


    @Step("Get checkboxes for section: {sectionName}")
    public JobPage getCheckboxes(String sectionName) {
        listOfSectionsCheckboxes.clear();
        String xpathCheckbox = String.format(formattedCheckboxesXpath, sectionName);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathCheckbox)));
        listOfSectionsCheckboxes.addAll(driver.findElements(By.xpath(xpathCheckbox)));
        logger.info("Checkboxes found for section: {}", sectionName);
        listOfSectionsCheckboxes.forEach(checkbox -> logger.debug("Checkbox found: {}", checkbox.getText()));
        return this;
    }

    @Step("Filter sections via random filter")
    public JobPage filterSectionViaRandomFilter() {
        try {
            int randomNumber = random.nextInt(listOfSectionsCheckboxes.size());
            lastSelectedFilter = listOfSectionsCheckboxes.get(randomNumber);
            logger.info("Selected random filter number: {}", randomNumber);

            javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});",
                    listOfSectionsCheckboxes.get(randomNumber));
            listOfSectionsCheckboxes.get(randomNumber).click();

            logger.info("Random selected filter: {}", listOfSectionsCheckboxes.get(randomNumber).getText());
            helper.compareCurrentURL_withPrevious();
            validateFilterCountAndResultsAreEquals(listOfSectionsCheckboxes.get(randomNumber));

        } catch (Exception e) {
            logger.error("Error filtering sections", e);
            ScreenshotUtil.attachScreenshot(driver,"screenshot");
            throw e;
        }
        return this;
    }

    @Step("validateFilterCountAndResultsAreEquals")
    public int[] validateFilterCountAndResultsAreEquals(WebElement element) {
        try {
            Integer filterCount = Integer.valueOf(element.getText().replaceAll("[^0-9]", ""));
            logger.info("Filter count is {}", filterCount);
            List<WebElement> filteredElements;
            int actualCount = 0;
            boolean paginationVisible = false;

            if (filterCount < 50) {
                List<WebElement> paginationElements = driver.findElements(By.xpath("//ul[contains(@class, 'pagination')]"));
                paginationVisible = !paginationElements.isEmpty();

                try {
                    filteredElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorFilteredItems));
                    actualCount = filteredElements.size();
                } catch (Exception e) {
                    filteredElements = new ArrayList<>();
                    actualCount = 0;
                }
            } else {
                wait.until(ExpectedConditions.visibilityOf(paginationMaxValueElement));
                javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", paginationMaxValueElement);
                Integer paginationMaxValue = Integer.valueOf(paginationMaxValueElement.getText());
                logger.info("Pagination max value: {}", paginationMaxValue);
                paginationMaxValueElement.click();
                helper.compareCurrentURL_withPrevious();
                filteredElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorFilteredItems));
                actualCount = (paginationMaxValue - 1) * 50 + filteredElements.size();
                paginationVisible = true;
            }

            return new int[]{filterCount, actualCount, paginationVisible ? 1 : 0};
        } catch (Exception e) {
            logger.error("Validation failed", e);
            ScreenshotUtil.attachScreenshot(driver);
            throw e;
        }
    }


    @Step("checkFirstAndSecondFiltersSumCountAndResultCount")
    public int[] checkFirstAndSecondFiltersSumCountAndResultCount(String filterGroupName) {
        // First filter
        int firstRandomIndex = random.nextInt(listOfSectionsCheckboxes.size());
        WebElement firstFilter = listOfSectionsCheckboxes.get(firstRandomIndex);
        int firstFilterCount = IntegerParseHelper.extractNumberFromElement(firstFilter);
        scrollIntoView(firstFilter);
        firstFilter.click();
        helper.compareCurrentURL_withPrevious();

        getCheckboxes(filterGroupName);

        // Second filter
        int secondRandomIndex = random.nextInt(listOfSectionsCheckboxes.size());
        while (secondRandomIndex == firstRandomIndex && listOfSectionsCheckboxes.size() > 1) {
            secondRandomIndex = random.nextInt(listOfSectionsCheckboxes.size());
        }
        WebElement secondFilter = listOfSectionsCheckboxes.get(secondRandomIndex);
            int secondFilterCount = IntegerParseHelper.extractNumberFromElement(secondFilter);
        scrollIntoView(secondFilter);
        secondFilter.click();
        helper.compareCurrentURL_withPrevious();

        List<WebElement> mixedResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorFilteredItems));
        int totalResults = mixedResults.size();

        selectedFilters = new ArrayList<>();
        selectedFilters.add(firstFilter);
        selectedFilters.add(secondFilter);

        return new int[]{firstFilterCount, secondFilterCount, totalResults};
    }

    private void scrollIntoView(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }

    public JobPage clearAllFilters() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(clearButton));
            scrollIntoView(clearButton);
            clearButton.click();
            wait.until(ExpectedConditions.invisibilityOf(clearButton));
        } catch (Exception e) {
            logger.error("Clear button not found or no filters to clear");
        }
        return this;
    }



    @Step("removeOneOfSelectedFiltersAndValidateResult")
    public int[] removeOneOfSelectedFiltersAndValidateResult() {
        int randomValue = random.nextInt(selectedFilters.size());
        WebElement toRemove = selectedFilters.get(randomValue);
        System.out.println("Removing element is : " + toRemove.getText());
        scrollIntoView(toRemove);
        wait.until(ExpectedConditions.visibilityOf(toRemove)).click();
        helper.compareCurrentURL_withPrevious();

        selectedFilters.remove(toRemove);

        if (!selectedFilters.isEmpty()) {
            WebElement selectedElement = selectedFilters.get(0);
            int[] validationResult = validateFilterCountAndResultsAreEquals(selectedElement);
            return new int[]{validationResult[0], validationResult[1]};
        } else {
            System.out.println("No filter for validating");
            return null;
        }
    }

    public WebElement getLastSelectedFilter() {
        return lastSelectedFilter;
    }
}