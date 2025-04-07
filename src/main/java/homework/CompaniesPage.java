package homework;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CompaniesPage extends BasePage {

    private final By selectorSearchPageInput = By.xpath("//input[@placeholder='Enter keywords...']");
    private final By selectorSearchButton = By.xpath("//div[text()='Search']");
    private final By selectorSearchResultCount = By.xpath("//img[@alt='building']//following-sibling::div");
    private WebElement searchPageInput;
    private WebElement searchButton;

    private final By selectorViewMore = By.xpath("//div[text()='Filter By Industry']/following-sibling::div//div[text()='View more']/parent::div");


    public void searchRandomString(String text) throws InterruptedException {
        searchPageInput = wait.until(ExpectedConditions.elementToBeClickable(selectorSearchPageInput));
        searchPageInput.click();
        searchPageInput.sendKeys(text);

        searchButton = wait.until(ExpectedConditions.elementToBeClickable(selectorSearchButton));
        searchButton.click();

        Thread.sleep(3000);//քանի որ չի հասցնում լոդ անի էսպես եմ թողել
    }

    public void checkResultCountIsZero() {
        String searchResultCount = wait.until(ExpectedConditions.visibilityOfElementLocated(selectorSearchResultCount)).getText();
        System.out.println(searchResultCount);
        Integer resultCount = Integer.parseInt(searchResultCount);
        Assertions.assertEquals(0, resultCount, "result isn't 0");
        System.out.println("The result is 0");
    }

    public void clearSearchInput()  {
        searchPageInput.clear();
    }


    //2

    public CompaniesPage clickToViewMoreButton()  {
        WebElement viewMore = wait.until(ExpectedConditions.elementToBeClickable(selectorViewMore));
        viewMore.click();
        return new CompaniesPage();
    }

    public ResultPage filterCompaniesByIndustry(String industryName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//div[text() = 'Filter By Industry']/parent::div//div/span[text()=\"%s\"]",
                        StringHelper.capitalizeFirstLetters(industryName)))));

        action.moveToElement(element).perform();//change to JS for scrolling
        element.click();
        return new ResultPage();

    }
}


