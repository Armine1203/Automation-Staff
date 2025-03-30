import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.CompaniesPage;
import pages.MainPage;
import pages.ResultPage;

public class Tests {
    static WebDriver driver;
    private MainPage mainPage;
    private CompaniesPage companiesPage;
    private ResultPage resultPage;

    @BeforeEach
    public void openWebPage() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("https://staff.am/");
        Thread.sleep(5000);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        companiesPage = new CompaniesPage(driver);
        resultPage = new ResultPage(driver);
        System.out.println("PreTest");
    }

    @AfterEach
    public void quitDriver() {
        System.out.println("PostTest");
//        driver.quit();
        }

    @Test
    public void testCompanySearch() throws InterruptedException {
        mainPage.clickCompaniesTab();
        Thread.sleep(3000);
        mainPage.searchIndustry();
        Thread.sleep(3000);
        companiesPage.searchRandomString("njvkdjvg");
        companiesPage.checkResultCountIsZero();
        companiesPage.clearSearchInput();
        companiesPage.searchRandomString("ser");
        resultPage.checkResultListItemsContainText("ser");
//        resultPage.checkResultListItemsContainText("ser++"); //Work correct
        resultPage.chooseRandomItem();

    }
}

