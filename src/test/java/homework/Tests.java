package homework;

import homework.Helpers.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;

public class Tests extends TestClass {
    MainPage mainPage = new MainPage();
    CompaniesPage companiesPage = new CompaniesPage();
    ResultPage resultPage = new ResultPage();


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
    public void test2()  {
        System.out.println();
        System.out.println("Test2");
        HeaderComponent header = new HeaderComponent();
        FooterComponent footer = new FooterComponent();
        header
                .clickOnCompaniesTab()
                .clickToViewMoreButton()
                .filterCompaniesByIndustry("sport");

        System.out.println("All companies");
        List<Company> allCompanyList = resultPage.addCompaniesDataToList();
        resultPage.clickHiringTab();

        System.out.println("Hiring");
        List<Company> hiringCompanyList = resultPage.addCompaniesDataToList();


        //--------------------------------
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
}
