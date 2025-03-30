package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompaniesPage {
    WebDriver driver;

    private final By selectorSearchPageInput = By.xpath("//input[@placeholder='Enter keywords...']");
    private final By selectorSearchButton = By.xpath("//div[text()='Search']");
    private final By selectorSearchResultCount = By.xpath("//img[@alt='building']//following-sibling::div");
    private WebElement searchPageInput;
    private WebElement searchButton;


    public CompaniesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchRandomString(String text) throws InterruptedException {
        searchPageInput = driver.findElement(selectorSearchPageInput);
        searchPageInput.click();
        searchPageInput.sendKeys(text);//Change this generate string function

        searchButton = driver.findElement(selectorSearchButton);
        searchButton.click();
        Thread.sleep(3000);
    }

    public void checkResultCountIsZero(){
        String searchResultCount = driver.findElement(selectorSearchResultCount).getText();
        System.out.println(searchResultCount);
        Integer resultCount = Integer.parseInt(searchResultCount);
        Assertions.assertEquals(0, resultCount, "result isn't 0");
        System.out.println("The result is 0");
    }

    public void clearSearchInput() throws InterruptedException {
        searchPageInput.clear();
    }
//    public boolean verifySearchResultsContain(String keyword) {
//        List<WebElement> results = driver.findElements(resultsList);
//        for (WebElement result : results) {
//            String companyName = result.findElement(resultsCompanyName).getText().toLowerCase();
//            if (!companyName.contains(keyword.toLowerCase())) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void selectFirstCompany() {
//        List<WebElement> results = driver.findElements(resultsList);
//        if (!results.isEmpty()) {
//            actions.moveToElement(results.get(0)).perform();
//            results.get(0).click();
//        }
//    }
//
//    public boolean verifyCompanyDetailsMatch() {
//        WebElement companyName = driver.findElement(resultsCompanyName);
//        By selectedCompanyName = By.xpath("//h1[@role='heading']");
//        String expectedName = companyName.getText();
//        String actualName = driver.findElement(selectedCompanyName).getText();
//        return expectedName.equals(actualName);
//    }
}


