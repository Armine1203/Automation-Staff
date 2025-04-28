package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultPage extends BasePage {

    @FindBy(xpath = "//div/a[@role='link']")
    private List<WebElement> listOfResults;

    @FindBy(xpath = "//div[text()='Hiring']")
    private WebElement hiringTab;

    private static int randomNumber;
    private List<Company> companyList = new ArrayList<>();
    private final Random random = new Random();

    @FindBy(xpath = "//h1[@role='heading']")
    private WebElement selectedPageCompanyName;

    @FindBy(xpath = "(//img[@alt='eye-icon']/parent::div/following-sibling::div)[1]")
    private WebElement selectedPageViews;

    @FindBy(xpath = "//img[@alt='people-icon']//parent::div//following-sibling::div")
    private WebElement selectedPageFollowers;

    @FindBy(xpath = "//div[contains(text(), 'active job')]")
    private WebElement selectedActiveJobsCount;

    @FindBy(xpath = "(//img[@alt='timer-icon']/parent::div/following-sibling::div)[1]")
    private WebElement selectedHistoryJobsCount;

    public List<WebElement> getListOfResults() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(listOfResults));
    }

    public void checkResultListItemsContainText(String text) {
        System.out.println("Companies");
        for (WebElement element : getListOfResults()) {
            String companyName = element.findElement(By.xpath(".//img[@alt='company-logo']/following-sibling::div/div[1]")).getText().toLowerCase();
            System.out.print(companyName + ", ");
        }
        System.out.println("\nAll names contain " + text);
    }

    public List<Company> addCompaniesDataToList() {
        companyList.clear();
        for (WebElement element : getListOfResults()) {
            String companyName = element.findElement(By.xpath(".//img[@alt='company-logo']/following-sibling::div/div[1]")).getText().toLowerCase();
            String pageViews = element.findElement(By.xpath(".//img[@alt='eye-icon']//following-sibling::div[1]")).getText().replaceAll("[^0-9]", "");
            String pageFollowers = element.findElement(By.xpath(".//img[@alt='people-icon']//following-sibling::div")).getText().replaceAll("[^0-9]", "");
            String activeJobsCount = element.findElement(By.xpath(".//div[contains(text(), 'Active job')]")).getText().replaceAll("[^0-9]", "");
            String historyJobsCount = element.findElement(By.xpath(".//div[contains(text(), 'Job history')]")).getText().replaceAll("[^0-9]", "");

            companyList.add(new Company(companyName,
                    Integer.parseInt(pageViews),
                    Integer.parseInt(pageFollowers),
                    Integer.parseInt(activeJobsCount),
                    Integer.parseInt(historyJobsCount)));
        }
        return companyList;
    }

    public void chooseRandomItem() {
        randomNumber = random.nextInt(getListOfResults().size());
        addCompaniesDataToList();
        WebElement element = getListOfResults().get(randomNumber);
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
        element.click();
    }

    public Company getSelectedCompanyData() {
        String name = selectedPageCompanyName.getText().toLowerCase();
        int views = Integer.parseInt(selectedPageViews.getText().replaceAll("[^0-9]", ""));
        int followers = Integer.parseInt(selectedPageFollowers.getText().replaceAll("[^0-9]", ""));
        int activeJobs = Integer.parseInt(selectedActiveJobsCount.getText().replaceAll("[^0-9]", ""));
        int historyJobs = Integer.parseInt(selectedHistoryJobsCount.getText().replaceAll("[^0-9]", ""));

        return new Company(name, views, followers, activeJobs, historyJobs);
    }

    public void clickHiringTab() {
        wait.until(ExpectedConditions.elementToBeClickable(hiringTab));
        hiringTab.click();
    }

    public Company getCompanyFromList() {
        return companyList.get(randomNumber);
    }
}
