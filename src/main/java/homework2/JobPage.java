package homework2;

import homework.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobPage extends BasePage {

    public JobPage() {
        PageFactory.initElements(driver, this);
    }

    private final Random random = new Random();
    private List<WebElement> listOfSectionsCheckboxes = new ArrayList<>();
    private final By clearAllFiltersButton = By.xpath("//div[contains(text(), 'Clear filters')]");
    private final String formattedCheckboxesXpath = "//div[text() =\"%s\"]/parent::div//div/span";
    private final By selectorPaginationMaxValue = By.xpath("//li[@class='next']//preceding-sibling::li[1]/a");
    private final By selectorFilteredItems = By.xpath("//h1[text()='Current Job Openings']/parent::div/following-sibling::div//a[@role ='link']");
    private final String formattedViewMoreButtonXpath = "//div[text()=\"%s\"]/following-sibling::div//div[text()='View more']/parent::div";
    private WebElement paginationMaxValueElement ;


    public JobPage clickToViewMoreButton(String sectionName) {
        try {
            String xpathViewMore = String.format(formattedViewMoreButtonXpath, sectionName);
            WebElement viewMore = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathViewMore)));
            if (viewMore.isDisplayed() && viewMore.isEnabled()) {
                scrollIntoView(viewMore);
                viewMore.click();
            }
        } catch (Exception e) {
            System.out.println("No 'View more' button for section: " + sectionName);
        }
        return this;
    }

    public JobPage getCheckboxes(String sectionName) {
        listOfSectionsCheckboxes.clear();
        String xpathCheckbox = String.format(formattedCheckboxesXpath, sectionName);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathCheckbox)));
        listOfSectionsCheckboxes.addAll(driver.findElements(By.xpath(xpathCheckbox)));

        System.out.println("Checkboxes found for section: " + sectionName);
        listOfSectionsCheckboxes.forEach(checkbox -> System.out.println(" - " + checkbox.getText()));

        return this;
    }
    public JobPage filterSectionViaRandomFilter() throws InterruptedException {
        int randomNumber = random.nextInt(listOfSectionsCheckboxes.size());
        System.out.println("randomNumber "+randomNumber);
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});",  listOfSectionsCheckboxes.get(randomNumber));
        listOfSectionsCheckboxes.get(randomNumber).click();
        System.out.println("Random selected filter : "+listOfSectionsCheckboxes.get(randomNumber).getText());
        validateFilterCountAndResultsAreEquals(listOfSectionsCheckboxes.get(randomNumber));
        return this;
    }
    public JobPage validateFilterCountAndResultsAreEquals(WebElement element) throws InterruptedException {
        Integer filterCount = Integer.valueOf(element.getText().replaceAll("[^0-9]", ""));
        System.out.println("filterCount is " + filterCount);
        List<WebElement> filteredElements;
        if (filterCount < 50) {
            //driver.findelement poxarinel
            List<WebElement> paginationElements = driver.findElements(By.xpath("//ul[contains(@class, 'pagination')]"));
            if (!paginationElements.isEmpty()) {
                System.out.println("Pagination should not be visible when filterCount < 50");
            }
            try {
                Thread.sleep(5000);//term, poxel
                filteredElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorFilteredItems));
                System.out.println("filteredElements.size() "+filteredElements.size());
            }catch (Exception e){
                filteredElements = new ArrayList<>();
                System.out.println("filteredElements.size() "+filteredElements.size());
            }
            System.out.println("filterCount: "+filterCount +", filteredElements.size():"+filteredElements.size());
            Assertions.assertEquals(filterCount,filteredElements.size(),"filterCount doesn't equals to result item's count");
        }else {
            Thread.sleep(5000);//term, poxel kam jnjel
            paginationMaxValueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selectorPaginationMaxValue));
            javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", paginationMaxValueElement);
            Integer paginationMaxValue = Integer.valueOf(paginationMaxValueElement.getText());
            System.out.println("paginationMaxValue: "+paginationMaxValue);
            paginationMaxValueElement.click();
            Thread.sleep(5000);
            filteredElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorFilteredItems));
            System.out.println("Last page filtered elements.size() "+filteredElements.size());
            Assertions.assertEquals(filterCount,(paginationMaxValue-1)*50+filteredElements.size());
        }
        return this;
    }

    public void checkFirstAndSecondFiltersSumCountAndResultCount(String filterGroupName) throws InterruptedException {
        clickToViewMoreButton(filterGroupName)
                .getCheckboxes(filterGroupName);
        //1-in filter
        int firstRandomIndex = random.nextInt(listOfSectionsCheckboxes.size());
        WebElement firstFilter = listOfSectionsCheckboxes.get(firstRandomIndex);
        int firstFilterCount = Helper.extractNumberFromElement(firstFilter);
        scrollIntoView(firstFilter);
        firstFilter.click();

        //2-rd filter
        getCheckboxes(filterGroupName);
        int secondRandomIndex = random.nextInt(listOfSectionsCheckboxes.size());
        while (secondRandomIndex == firstRandomIndex && listOfSectionsCheckboxes.size() > 1) {
            secondRandomIndex = random.nextInt(listOfSectionsCheckboxes.size());
        }
        WebElement secondFilter = listOfSectionsCheckboxes.get(secondRandomIndex);
        int secondFilterCount = Helper.extractNumberFromElement(secondFilter);
        scrollIntoView(secondFilter);
        secondFilter.click();

        List<WebElement> mixedResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorFilteredItems));;
        int totalResults = mixedResults.size();

        System.out.println("First filter count:"+firstFilterCount+"Second filter count: "+secondFilterCount+"Total result: "+totalResults);
        Assertions.assertTrue(totalResults <= firstFilterCount+secondFilterCount,"Total result should be less than or equal to both filter counts sum");
        clearAllFilters();
    }


    private void scrollIntoView(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }

    public JobPage clearAllFilters() {
        try {
            WebElement clearButton = wait.until(ExpectedConditions.elementToBeClickable(clearAllFiltersButton));
            scrollIntoView(clearButton);
            clearButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(clearAllFiltersButton));
        } catch (Exception e) {
            System.out.println("Clear button not found or no filters to clear");
        }
        return this;
    }
}