package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompaniesPage extends BasePage {

    private final By selectorSearchPageInput = By.xpath("//input[@placeholder='Enter keywords...']");
    private final By selectorSearchButton = By.xpath("//div[text()='Search']");
    private final By selectorSearchResultCount = By.xpath("//img[@alt='building']//following-sibling::div");
    private WebElement searchPageInput;
    private WebElement searchButton;


    public void searchRandomString(String text) throws InterruptedException {
        searchPageInput = driver.findElement(selectorSearchPageInput);
        searchPageInput.click();
        searchPageInput.sendKeys(text);//Change this generate string function

        searchButton = driver.findElement(selectorSearchButton);
        searchButton.click();
        Thread.sleep(3000);
    }

    public void checkResultCountIsZero() {
        String searchResultCount = driver.findElement(selectorSearchResultCount).getText();
        System.out.println(searchResultCount);
        Integer resultCount = Integer.parseInt(searchResultCount);
        Assertions.assertEquals(0, resultCount, "result isn't 0");
        System.out.println("The result is 0");
    }

    public void clearSearchInput() throws InterruptedException {
        searchPageInput.clear();
    }


    //2

    public CompaniesPage clickToViewMoreButton() throws InterruptedException {
        Thread.sleep(3000);
        WebElement viewMore = driver.findElement(By.xpath("//div[text()='Filter By Industry']/following-sibling::div//div[text()='View more']/parent::div"));
        viewMore.click();
        return new CompaniesPage();
    }

    public ResultPage filterCompaniesByIndustry(String industryName) throws InterruptedException {

        WebElement element = driver.findElement(By.xpath(String.format("//div[text() = 'Filter By Industry']/parent::div//div/span[text()=\"%s\"]", StringHelper.capitalizeFirstLetters(industryName))));
        action.moveToElement(element).perform();
        element.click();
        return new ResultPage();

    }
}


