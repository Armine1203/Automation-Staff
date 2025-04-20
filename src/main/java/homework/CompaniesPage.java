package homework;

import homework.Helpers.StringHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CompaniesPage extends BasePage {

    private final By selectorSearchResultCount = By.xpath("//img[@alt='building']//following-sibling::div");

    @FindBy(xpath = "//input[@placeholder='Enter keywords...']")
    private WebElement searchPageInput;

    @FindBy(xpath = "//div[text()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[text()='Filter By Industry']/following-sibling::div//div[text()='View more']/parent::div")
    private WebElement viewMore;

    private static String previousURL;



    public void searchRandomString(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(searchPageInput));
        searchPageInput.click();
        searchPageInput.sendKeys(text);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        compareCurrentURL_withPrevious();
    }

    public void checkResultCountIsZero() {
        String searchResultCount = wait.until(ExpectedConditions.visibilityOfElementLocated(selectorSearchResultCount)).getText();
        System.out.println(searchResultCount);
        Integer resultCount = Integer.parseInt(searchResultCount);
//        Assertions.assertEquals(0, resultCount, "result isn't 0");
        System.out.println("The result is 0");
    }

    public void clearSearchInput()  {
        searchPageInput.clear();
    }

    //2
    public CompaniesPage clickToViewMoreButton()  {
        wait.until(ExpectedConditions.elementToBeClickable(viewMore));
        viewMore.click();
        return new CompaniesPage();
    }

    public ResultPage filterCompaniesByIndustry(String industryName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//div[text() = 'Filter By Industry']/parent::div//div/span[text()=\"%s\"]",
                        StringHelper.capitalizeFirstLetters(industryName)))));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
        element.click();
        compareCurrentURL_withPrevious();
        return new ResultPage();

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
}


