package homework2;

import homework.BasePage;
import homework.Company;
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

    Random random = new Random();
    By selectorCheckbox = By.xpath("//div[text() = 'Job category']/parent::div//div/span");
    List<WebElement> listOfSectionsCheckboxes = new ArrayList<>();



    private final String formatedViewMoreButtonXpath = "//div[text()=\"%s\"]/following-sibling::div//div[text()='View more']/parent::div";

    public JobPage clickToViewMoreButton(String sectionName) {
        try {
            String xpathViewMore = String.format(formatedViewMoreButtonXpath, sectionName);
            WebElement viewMore = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathViewMore)));
            if (viewMore.isDisplayed() && viewMore.isEnabled()) {
                javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", viewMore);
                viewMore.click();
            }
        } catch (Exception e) {
            System.out.println("No 'View more' button for section: " + sectionName);
        }
        return this;
    }

    private final String formatedCheckboxesXpath = "//div[text() =\"%s\"]/parent::div//div/span";


    public JobPage getCheckboxes(String sectionName) {

        listOfSectionsCheckboxes.clear();
        String xpathCheckbox = String.format(formatedCheckboxesXpath, sectionName);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathCheckbox)));

        List<WebElement> checkboxes = driver.findElements(By.xpath(xpathCheckbox));

        System.out.println("Checkboxes found for section: " + sectionName);
        for (WebElement checkbox : checkboxes) {
            System.out.println(" - " + checkbox.getText());
            listOfSectionsCheckboxes.add(checkbox);
        }

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

    By selectorPaginationMaxValue = By.xpath("//li[@class='next']//preceding-sibling::li[1]/a");
    WebElement paginationMaxValueElement ;
    By selectorFilteredItems = By.xpath("//h1[text()='Current Job Openings']/parent::div/following-sibling::div//a[@role ='link']");

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
}

//121=2*50+21
//filtercount = paginitionMaxValue-1*50+filteredElements.size()
//
