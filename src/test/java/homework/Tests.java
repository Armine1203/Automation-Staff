package homework;

import homework2.JobPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class Tests extends TestClass {
    MainPage mainPage = new MainPage();
    CompaniesPage companiesPage = new CompaniesPage();
    ResultPage resultPage = new ResultPage();
    JobPage jobPage = new JobPage();

    @Test
    public void testCompanySearch() throws InterruptedException {
        System.out.println("Test1");
        companiesPage = mainPage.clickCompaniesTab().searchIndustry();
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

        Thread.sleep(5000);// new sport companies doesn't load without this row
        System.out.println("All companies");
        List<Company> allCompanyList = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();

        Thread.sleep(5000);
        System.out.println("Hiring");
        List<Company> hiringCompanyList = resultPage.addCompaniesDataToList();


        //--------------------------------
        Thread.sleep(5000);
        footer.clickCompaniesViewAllCompaniesTab();

        //repeat steps from 2 to 4
        companiesPage.filterCompaniesByIndustry("sport");

        System.out.println("All companies2");
        List<Company> allCompanyList2 = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();

        System.out.println("Hiring2");
        List<Company> hiringCompanyList2 = resultPage.addCompaniesDataToList();

        Assertions.assertEquals(allCompanyList, allCompanyList2, "All companies data doesn't equal");
        Assertions.assertEquals(hiringCompanyList, hiringCompanyList2, "Hiring companies data doesn't equal");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Job category",
            "Job special tag",
            "Specialist level",
            "Job salary",
            "Job types",
            "Job terms",
            "By cities"
    })
    public void test3(String filterGroupName) throws InterruptedException {
        System.out.println("Test3");
        //1
        System.out.println("click to view more button, " +
                            "get all checkboxes under the filter-group," +
                            "filter section via random filter" +
                            "validate filter count and results equal(validation function in filterSectionViaRandomFilter())" +
                            "also I check pagination in validateFilterCountAndResultsAreEquals()");
        jobPage.clickToViewMoreButton(filterGroupName)
                .getCheckboxes(filterGroupName)
                .filterSectionViaRandomFilter();
        jobPage.clearAllFilters();


        //2
        System.out.println("------------------------------------------------------------");
        System.out.println("choose two filters , check they work correct together");
        jobPage.checkFirstAndSecondFiltersSumCountAndResultCount(filterGroupName);


        //3
        System.out.println("------------------------------------------------------------");
        System.out.println("then remove one of them and validate other works correct ");
        jobPage.removeOneOfSelectedFiltersAndValidateResult();

    }
}
