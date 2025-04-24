package homework2;

import homework.BasePage;
import homework.Helpers.IntegerParseHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobPage extends BasePage {

    private String previousURL;

    public JobPage() {
        PageFactory.initElements(driver, this);
    }

    private final Random random = new Random();

    @FindBy(xpath = "//div[contains(text(), 'Clear filters')]")
    private WebElement clearButton;

    @FindBy(xpath = "//li[@class='next']//preceding-sibling::li[1]/a")
    private WebElement paginationMaxValueElement;

    @FindBy(xpath = "//h1[text()='Current Job Openings']/parent::div/following-sibling::div//a[@role ='link']")
    private List<WebElement> filteredItems;

    private WebElement lastSelectedFilter;
    private List<WebElement> listOfSectionsCheckboxes = new ArrayList<>();
    private static List<WebElement> selectedFilters;

    private final String formattedCheckboxesXpath = "//div[text() =\"%s\"]/parent::div//div/span";
    private final String formattedViewMoreButtonXpath = "//div[text()=\"%s\"]/following-sibling::div//div[text()='View more']/parent::div";

    @Step("Click to view more button for section: {sectionName}")
    public JobPage clickToViewMoreButton(String sectionName) {
        try {
            String xpathViewMore = String.format(formattedViewMoreButtonXpath, sectionName);
            WebElement viewMore = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathViewMore)));
            if (viewMore.isDisplayed() && viewMore.isEnabled()) {
                scrollIntoView(viewMore);
                viewMore.click();
                logger.info("Clicked 'View more' button for section: {}", sectionName);
            }
        } catch (Exception e) {
            logger.error("No 'View more' button for section: {}", sectionName, e);
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
            compareCurrentURL_withPrevious();
            getFilterValidationResult();

        } catch (Exception e) {
            logger.error("Error filtering sections", e);
            throw e;
        }
        return this;
    }

    @Step("Validate filter count and results")
    public FilterValidationResult getFilterValidationResult() {
        try {
            Integer filterCount = Integer.valueOf(lastSelectedFilter.getText().replaceAll("[^0-9]", ""));
            logger.info("Filter count is {}", filterCount);
            int actualCount;
            boolean paginationVisible;

            if (filterCount < 50) {
                paginationVisible = !driver.findElements(By.xpath("//ul[contains(@class, 'pagination')]")).isEmpty();
                try {
                    actualCount = filteredItems.size();
                } catch (Exception e) {
                    actualCount = 0;
                }
            } else {
                wait.until(ExpectedConditions.visibilityOf(paginationMaxValueElement));
                javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", paginationMaxValueElement);
                Integer paginationMaxValue = Integer.valueOf(paginationMaxValueElement.getText());
                logger.info("Pagination max value: {}", paginationMaxValue);
                paginationMaxValueElement.click();
                compareCurrentURL_withPrevious();
                actualCount = (paginationMaxValue - 1) * 50 + filteredItems.size();
                paginationVisible = true;
            }
            return new FilterValidationResult(filterCount, actualCount, paginationVisible);
        } catch (Exception e) {
            logger.error("Validation failed", e);
            throw e;
        }
    }

    @Step("Check first and second filters sum count and result count")
    public List<Integer> getFirstAndSecondFiltersSumCountAndResultCount(String filterGroupName) {
        // First filter
        int firstRandomIndex = random.nextInt(listOfSectionsCheckboxes.size());
        WebElement firstFilter = listOfSectionsCheckboxes.get(firstRandomIndex);
        int firstFilterCount = IntegerParseHelper.extractNumberFromElement(firstFilter);
        scrollIntoView(firstFilter);
        firstFilter.click();
        compareCurrentURL_withPrevious();
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
        compareCurrentURL_withPrevious();

        int totalResults = filteredItems.size();
        selectedFilters = new ArrayList<>();
        selectedFilters.add(firstFilter);
        selectedFilters.add(secondFilter);
        return List.of(firstFilterCount, secondFilterCount, totalResults);
    }

    @Step("Remove one of selected filters and validate result")
    public FilterValidationResult removeOneOfSelectedFiltersAndValidateResult() {
        int randomValue = random.nextInt(selectedFilters.size());
        WebElement toRemove = selectedFilters.get(randomValue);
        System.out.println("Removing element is : " + toRemove.getText());
        scrollIntoView(toRemove);
        wait.until(ExpectedConditions.visibilityOf(toRemove)).click();
        compareCurrentURL_withPrevious();
        selectedFilters.remove(toRemove);

        if (!selectedFilters.isEmpty()) {
            lastSelectedFilter = selectedFilters.get(0);
            return getFilterValidationResult();
        } else {
            System.out.println("No filter for validating");
            return null;
        }
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


    public void compareCurrentURL_withPrevious() throws TimeoutException {
        try {
            previousURL = driver.getCurrentUrl();
            System.out.println("previousURL " + previousURL);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(previousURL)));
            System.out.println("current url " + driver.getCurrentUrl());
        } catch (TimeoutException timeoutException) {
            System.out.println("TimeoutException");
        }
    }

    public static class FilterValidationResult {
        private final int filterCount;
        private final int actualCount;
        private final boolean paginationVisible;

        public FilterValidationResult(int filterCount, int actualCount, boolean paginationVisible) {
            this.filterCount = filterCount;
            this.actualCount = actualCount;
            this.paginationVisible = paginationVisible;
        }

        public int getFilterCount() { return filterCount; }
        public int getActualCount() { return actualCount; }
        public boolean isPaginationVisible() { return paginationVisible; }
    }

}