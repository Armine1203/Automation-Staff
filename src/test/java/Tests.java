import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.util.List;

public class Tests extends TestClass {
    MainPage mainPage = new MainPage();
    CompaniesPage companiesPage = new CompaniesPage();
    ResultPage resultPage = new ResultPage();

    @Test
    public void testCompanySearch() throws InterruptedException {
        System.out.println("Test1");
        companiesPage = mainPage.clickCompaniesTab().searchIndustry();

        Thread.sleep(5000);
        companiesPage.searchRandomString(StringHelper.generateRandomString(8));
        companiesPage.checkResultCountIsZero();
        companiesPage.clearSearchInput();
        companiesPage.searchRandomString("ser");
        resultPage.checkResultListItemsContainText("ser");
//        resultPage.checkResultListItemsContainText("ser++"); //Work correct
        resultPage.chooseRandomItem();
        resultPage.checkTwoCompanyElementsData();
    }

    @Test
    public void test2() throws InterruptedException {
        System.out.println();
        System.out.println("Test2");
        HeaderComponent header = new HeaderComponent();
        FooterComponent footer = new FooterComponent();
        header
                .clickOnCompaniesTab()
                .clickToViewMoreButton()
                .filterCompaniesByIndustry("sport");
        Thread.sleep(3000);

        System.out.println("All companies");
        List<Company> allCompanyList = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();

        Thread.sleep(3000);
        System.out.println("Hiring");
        List<Company> hiringCompanyList = resultPage.addCompaniesDataToList();
        Thread.sleep(3000);

        //--------------------------------

        footer.clickCompaniesViewAllCompaniesTab();
        Thread.sleep(3000);
        //repeat steps from 2 to 4
        companiesPage.filterCompaniesByIndustry("sport");
        Thread.sleep(3000);
        List<Company> allCompanyList2 = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();

        Thread.sleep(3000);
        System.out.println("Hiring");
        List<Company> hiringCompanyList2 = resultPage.addCompaniesDataToList();
        Thread.sleep(3000);

        Assertions.assertEquals(allCompanyList,allCompanyList2,"All companies data doesn't equal");
        Assertions.assertEquals(hiringCompanyList,hiringCompanyList2,"Hiring companies data doesn't equal");




    }
}

