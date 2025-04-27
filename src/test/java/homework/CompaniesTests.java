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
    public void testCompanySearch() {
        logger.info("Starting testCompanySearch test");
        companiesPage = mainPage.clickCompaniesTab().searchIndustry();

        String randomString = StringHelper.generateRandomString(8);
        companiesPage.searchRandomString(randomString);
        int resultCount = companiesPage.getResultCount();
        Assertions.assertEquals(0, resultCount, "Result count should be 0 after searching: " + randomString);

        companiesPage.clearSearchInput();
        companiesPage.searchRandomString("ser");
        resultPage.checkResultListItemsContainText("ser");

        resultPage.chooseRandomItem();
        Company expectedCompany = resultPage.getCompanyFromList();
        Company actualCompany = resultPage.getSelectedCompanyData();

        Assertions.assertEquals(expectedCompany.getName().trim().toLowerCase(), actualCompany.getName().trim().toLowerCase(), "Company names do not match");
        Assertions.assertEquals(expectedCompany.getPageViews(), actualCompany.getPageViews(), "Page views count does not match");
        Assertions.assertEquals(expectedCompany.getPageFollowers(), actualCompany.getPageFollowers(), "Page followers count does not match");
        Assertions.assertEquals(expectedCompany.getActiveJobsCount(), actualCompany.getActiveJobsCount(), "Active jobs count does not match");
        Assertions.assertEquals(expectedCompany.getHistoryJobsCount(), actualCompany.getHistoryJobsCount(), "History jobs count does not match");

        logger.info("Ending testCompanySearch test");
    }

    @Test
    public void filterCompaniesByIndustries() {
        logger.info("Starting filterCompaniesByIndustries test");
        HeaderComponent header = new HeaderComponent();
        FooterComponent footer = new FooterComponent();

        header.clickOnCompaniesTab()
                .clickToViewMoreButton()
                .filterCompaniesByIndustry("sport");

        List<Company> allCompanyList = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();
        List<Company> hiringCompanyList = resultPage.addCompaniesDataToList();

        footer.clickCompaniesViewAllCompaniesTab();

        companiesPage.filterCompaniesByIndustry("sport");
        List<Company> allCompanyList2 = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();
        List<Company> hiringCompanyList2 = resultPage.addCompaniesDataToList();

        Assertions.assertEquals(allCompanyList, allCompanyList2, "All companies data doesn't equal");
        Assertions.assertEquals(hiringCompanyList, hiringCompanyList2, "Hiring companies data doesn't equal");

        logger.info("Ending filterCompaniesByIndustries test");
    }
}
