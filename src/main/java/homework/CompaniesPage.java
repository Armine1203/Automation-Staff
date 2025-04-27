package homework;

import homework.Helpers.StringHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CompaniesPage extends BasePage {

    @FindBy(xpath = "//img[@alt='building']//following-sibling::div")
    private WebElement searchResultCount;

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

    public int getResultCount() {
        wait.until(ExpectedConditions.visibilityOf(searchResultCount));
        String searchResultCountText = searchResultCount.getText();
        System.out.println(searchResultCountText);
        return Integer.parseInt(searchResultCountText);
    }

    public void clearSearchInput()  {
        searchPageInput.clear();
    }

    public CompaniesPage clickToViewMoreButton()  {
        wait.until(ExpectedConditions.elementToBeClickable(viewMore));
        viewMore.click();
        return new CompaniesPage();
    }

    public ResultPage filterCompaniesByIndustry(String industryName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                getDynamicIndustryElement(industryName)));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
        element.click();
        compareCurrentURL_withPrevious();
        return new ResultPage();
    }

    private WebElement getDynamicIndustryElement(String industryName) {
        return driver.findElement(By.xpath(String.format("//div[text()='Filter By Industry']/parent::div//div/span[text()=\"%s\"]",
                StringHelper.capitalizeFirstLetters(industryName))));
    }

    public void compareCurrentURL_withPrevious() {
        try {
            previousURL = driver.getCurrentUrl();
            System.out.println("previousURL " + previousURL);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(previousURL)));
            System.out.println("current url " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("TimeoutException or other error: " + e.getMessage());
        }
    }
}
