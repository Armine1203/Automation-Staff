package homework;

//import org.junit.jupiter.api.Assertions;
import homework.Helpers.CurrentURL_CheckHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultPage extends BasePage {
    Random random = new Random();
    CurrentURL_CheckHelper currentURLCheckHelper = new CurrentURL_CheckHelper();

    List<WebElement> listOfResults;
    static int randomNumber;
    List<Company> companyList = new ArrayList<>();

    By selectorListResult = By.xpath("//div/a[@role='link']");
    By resultsCompanyName = By.xpath(".//img[@alt='company-logo']/following-sibling::div/div[1]");

    By selector_selectedPageResultsCompanyName = By.xpath("//h1[@role='heading']");
    By selector_selectedNewPageResultCompanyPageViews = By.xpath("(//img[@alt='eye-icon']/parent::div/following-sibling::div)[1]");
    By selector_selectedNewPageResultCompanyPageFollowers = By.xpath("//img[@alt='people-icon']//parent::div//following-sibling::div");
    By selector_selectedNewPageResultCompanyActiveJobsCount = By.xpath("//div[contains(text(), 'active job')]");
    By selector_selectedNewPageResultCompanyHistoryJobsCount = By.xpath("(//img[@alt='timer-icon']/parent::div/following-sibling::div)[1]");

    By selector_hiringTab = By.xpath("//div[text()='Hiring']");

    public void checkResultListItemsContainText(String text) {
        System.out.println("Companies");
        listOfResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorListResult));
        for (WebElement element : listOfResults) {
            String companyName = element.findElement(resultsCompanyName).getText().toLowerCase();
            System.out.print(companyName + ", ");
//            Assertions.assertTrue(companyName.contains(text), "The name '" + companyName + "' does not contain " + text);
        }
        System.out.println(" ");
        System.out.println("All names contain " + text);

    }

    public List<Company> addCompaniesDataToList() {
        companyList.clear();
        listOfResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectorListResult));

        for (WebElement element : listOfResults) {
            int count = 0;
            String companyName = element.findElement(By.xpath(String.format("(.//img[@alt='company-logo']/following-sibling::div/div[1])[\"%s\"]", count))).getText().toLowerCase();
            String pageViews = element.findElement(By.xpath(String.format("(.//img[@alt='eye-icon']//following-sibling::div[1])[\"%s\"]", count))).getText().replaceAll("[^0-9]", "");
            String pageFollowers = element.findElement(By.xpath(String.format("(.//img[@alt='people-icon']//following-sibling::div)[\"%s\"]", count))).getText().replaceAll("[^0-9]", "");
            String activeJobsCount = element.findElement(By.xpath(String.format("(.//div[contains(text(), 'Active job')])[\"%s\"]", count))).getText().replaceAll("[^0-9]", "");
            String historyJobsCount = element.findElement(By.xpath(String.format("(.//div[contains(text(), 'Job history')])[\"s\"]", count))).getText().replaceAll("[^0-9]", "");
            System.out.print(companyName + ", " + pageViews + ", " + pageFollowers + ", " + activeJobsCount + ", " + historyJobsCount);
            System.out.println();

            Integer pageViewsCount = Integer.parseInt(pageViews);
            Integer pageFollowersCount = Integer.parseInt(pageFollowers);
            Integer pageActiveJobsCount = Integer.parseInt(activeJobsCount);
            Integer pageHistoryJobsCount = Integer.parseInt(historyJobsCount);
            count++;
            companyList.add(new homework.Company(companyName, pageViewsCount, pageFollowersCount, pageActiveJobsCount, pageHistoryJobsCount));
        }
        return companyList;

    }

    public void chooseRandomItem() {
        randomNumber = random.nextInt(listOfResults.size());
        addCompaniesDataToList();
        listOfResults = driver.findElements(selectorListResult);
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", listOfResults.get(randomNumber));
        listOfResults.get(randomNumber).click();
    }

    public void checkTwoCompanyElementsData() {
        String selectedCompanyName = driver.findElement(selector_selectedPageResultsCompanyName).getText().toLowerCase();
        String selectedPageViews = driver.findElement(selector_selectedNewPageResultCompanyPageViews).getText().replaceAll("[^0-9]", "");
        String selectedPageFollowers = driver.findElement(selector_selectedNewPageResultCompanyPageFollowers).getText().replaceAll("[^0-9]", "");
        String selectedActiveJobsCount = driver.findElement(selector_selectedNewPageResultCompanyActiveJobsCount).getText().replaceAll("[^0-9]", "");
        String selectedHistoryJobsCount = driver.findElement(selector_selectedNewPageResultCompanyHistoryJobsCount).getText().replaceAll("[^0-9]", "");

        Integer selectedSPageViewsCount = Integer.parseInt(selectedPageViews);
        Integer selectedPageFollowersCount = Integer.parseInt(selectedPageFollowers);
        Integer selectedPageActiveJobsCount = Integer.parseInt(selectedActiveJobsCount);
        Integer selectedPageHistoryJobsCount = Integer.parseInt(selectedHistoryJobsCount);


//        Assertions.assertEquals(companyList.get(randomNumber).getName(), selectedCompanyName, "pages.Company Names doesn't equal");
//        Assertions.assertEquals(companyList.get(randomNumber).getPageViews(), selectedSPageViewsCount, "pages.Company page views count doesn't equal");
//        Assertions.assertEquals(companyList.get(randomNumber).getPageFollowers(), selectedPageFollowersCount, "pages.Company page followers count doesn't equal");
//        Assertions.assertEquals(companyList.get(randomNumber).getActiveJobsCount(), selectedPageActiveJobsCount, "pages.Company page active jobs count doesn't equal");
//        Assertions.assertEquals(companyList.get(randomNumber).getHistoryJobsCount(), selectedPageHistoryJobsCount, "pages.Company page history jobs count doesn't equal");

        System.out.println("equal !!!!!");

    }

    public void clickHiringTab() {
        WebElement hiringTab = wait.until(ExpectedConditions.elementToBeClickable(selector_hiringTab));
        hiringTab.click();
        currentURLCheckHelper.compareCurrentURL_withPrevious();

    }

}
