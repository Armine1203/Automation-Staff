package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

public class ResultPage {
    WebDriver driver;
    Actions action;
    Random random = new Random();
    List<WebElement> listOfResults;
    int randomNumber = random.nextInt(listOfResults.size());
    List<Company> companyList;

    By selectorListResult = By.xpath("//div/a[@role='link']");
    By resultsCompanyName = By.xpath(".//img[@alt='company-logo']/following-sibling::div/div[1]");

    By selector_selectedPageResultsCompanyName = By.xpath("//h1[@role='heading']");

    By selector_resultCompanyPageViews = By.xpath("(//img[@alt='eye-icon']//following-sibling::div[1])[1]");
    WebElement resultCompanyPageViews;
    By selector_selectedNewPageResultCompanyPageViews = By.xpath("(//img[@alt='eye-icon']/parent::div/following-sibling::div)[1]");

    By selector_resultCompanyPageFollowers = By.xpath(".//img[@alt='people-icon']//following-sibling::div");
    WebElement resultCompanyPageFollowers;
    By selector_selectedNewPageResultCompanyPageFollowers = By.xpath(".//img[@alt='people-icon']//following-sibling::div");

    By selector_resultCompanyActiveJobsCount = By.xpath(".//div[contains(text(), 'Active job')]");
    WebElement resultCompanyActiveJobsCount;
    By selector_selectedNewPageResultCompanyActiveJobsCount = By.xpath(".//div[contains(text(), 'Active job')]");

    By selector_resultCompanyHistoryJobsCount = By.xpath(".//div[contains(text(), 'Job history')]");
    WebElement resultCompanyHistoryJobsCount;
    By selector_selectedNewPageResultCompanyHistoryJobsCount = By.xpath(".//div[contains(text(), 'Job history')]");



    public ResultPage(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
    }

    public void checkResultListItemsContainText(String text){
        System.out.println("Companies");
        listOfResults = driver.findElements(selectorListResult);
        for (WebElement element : listOfResults) {
            String companyName = element.findElement(resultsCompanyName).getText().toLowerCase();
            System.out.print(companyName + ", ");
            Assertions.assertTrue(companyName.contains(text), "The name '" + companyName + "' does not contain "+text);
        }
        System.out.println(" ");
        System.out.println("All names contain "+text);

    }

    private void addCompaniesDataToList(){
        for (WebElement element: listOfResults){
            int count = 0;
            String companyName = element.findElement(By.xpath(String.format("(.//img[@alt='company-logo']/following-sibling::div/div[1])[\"%s\"]",count))).getText().toLowerCase();
            System.out.println(companyName);

            String pageViews = element.findElement(By.xpath(String.format("(//img[@alt='eye-icon']//following-sibling::div[1])[\"%s\"]",count))).getText().toLowerCase();
            count++;
        }
    }


    public void chooseRandomItem(){
        addCompaniesDataToList();
        listOfResults = driver.findElements(selectorListResult);
        action.moveToElement(listOfResults.get(randomNumber)).perform();//scroll-ի ալտերնատիվ տարբերակ եմ օգտոգործել որպես
        listOfResults.get(randomNumber).click();
    }

    private void checkTwoElementsData(WebElement element1, By actualData, String text, String errorMessage) {
        WebElement selected_element2 = driver.findElement(actualData);
        String textOfElement1 = element1.getText().replaceAll("[^0-9]", "");
        String textOfSelectedElement2 = selected_element2.getText().replaceAll("[^0-9]", "");
        int expectedResult = Integer.parseInt(textOfElement1);
        int actualResult = Integer.parseInt(textOfSelectedElement2);
        System.out.println(text + " - expected result: " + expectedResult);
        System.out.println(text + " - actual result: " + actualResult);
        Assertions.assertEquals(expectedResult, actualResult, errorMessage);

    }
}
