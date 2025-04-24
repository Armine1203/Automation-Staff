package homework;

import homework.Helpers.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

public class CompaniesTests extends TestClass {
    MainPage mainPage = new MainPage();
    CompaniesPage companiesPage = new CompaniesPage();
    ResultPage resultPage = new ResultPage();
    private static final Logger logger = LoggerFactory.getLogger(CompaniesTests.class);


    @Test
    public void testCompanySearch() throws InterruptedException {
        logger.info("Starting testCompanySearch test");
        companiesPage = mainPage.clickCompaniesTab().searchIndustry();
        companiesPage.searchRandomString(StringHelper.generateRandomString(8));
        companiesPage.checkResultCountIsZero();
        companiesPage.clearSearchInput();
        companiesPage.searchRandomString("ser");
        resultPage.checkResultListItemsContainText("ser");
//        resultPage.checkResultListItemsContainText("ser++"); //Work correct
        resultPage.chooseRandomItem();
        resultPage.checkTwoCompanyElementsData();
        logger.info("Ending testCompanySearch test");
    }


    @Test
    public void filterCompaniesByIndustries()  {
        logger.info("Starting test2 test");
        HeaderComponent header = new HeaderComponent();
        FooterComponent footer = new FooterComponent();
        header
                .clickOnCompaniesTab()
                .clickToViewMoreButton()
                .filterCompaniesByIndustry("sport");

        logger.info("All companies");
        List<Company> allCompanyList = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();

        logger.info("Hiring");
        List<Company> hiringCompanyList = resultPage.addCompaniesDataToList();


        //--------------------------------
        footer.clickCompaniesViewAllCompaniesTab();

        //repeat steps from 2 to 4
        companiesPage.filterCompaniesByIndustry("sport");

        logger.info("All companies2");
        List<Company> allCompanyList2 = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();

        logger.info("Hiring2");
        List<Company> hiringCompanyList2 = resultPage.addCompaniesDataToList();

        Assertions.assertEquals(allCompanyList, allCompanyList2, "All companies data doesn't equal");
        Assertions.assertEquals(hiringCompanyList, hiringCompanyList2, "Hiring companies data doesn't equal");
    }
}
