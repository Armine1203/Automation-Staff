package homework2.tests;

import homework2.JobPage;
import homework2.TestClass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tests2 extends TestClass2 {
    private static final Logger logger = LoggerFactory.getLogger(Tests2.class);
    JobPage jobPage = new JobPage();

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
    public void test3(String filterGroupName) {
        logger.info("Starting Test3 for filter group: {}", filterGroupName);
        //1

        try {
            logger.info("Click to view more button, get all checkboxes, filter via random filter");
            jobPage.clickToViewMoreButton(filterGroupName)
                    .getCheckboxes(filterGroupName)
                    .filterSectionViaRandomFilter();

            int[] result = jobPage.validateFilterCountAndResultsAreEquals(jobPage.getLastSelectedFilter());
            int filterCount = result[0];
            int actualCount = result[1];
            boolean paginationVisible = result[2] == 1;
            logger.info("Validation results - Filter count: {}, Actual count: {}, Pagination visible: {}",
                    filterCount, actualCount, paginationVisible);

            if (filterCount < 50) {
                Assertions.assertFalse(paginationVisible,
                        "Pagination should not be visible when filterCount < 50");
                Assertions.assertEquals(filterCount, actualCount,
                        "filterCount doesn't equal to result item's count");
            } else {
                Assertions.assertEquals(filterCount, actualCount,
                        "Filter count doesn't match calculated pagination results");
            }
            jobPage.clearAllFilters();
            logger.info("Test3 completed succesfully for filter group : {}", filterGroupName);
        } catch (Exception e) {
            logger.error("Test3 failed for filter group: {}", filterGroupName, e);
        }
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
    public void test4(String filterGroupName) {
        logger.info("Starting Test4 for filter group: {}", filterGroupName);

        //2
        try {
            jobPage.clickToViewMoreButton(filterGroupName);
            jobPage.getCheckboxes(filterGroupName);
            logger.info("choose two filters , check they work correct together");
            int[] result = jobPage.checkFirstAndSecondFiltersSumCountAndResultCount(filterGroupName);
            int firstFilterCount = result[0];
            int secondFilterCount = result[1];
            int totalResults = result[2];
            logger.info("First filter count: {}, Second filter count: {}, Total result: {}",
                    firstFilterCount, secondFilterCount, totalResults);

            Assertions.assertTrue(totalResults <= firstFilterCount + secondFilterCount,
                    "Total result should be less than or equal to both filter counts sum");


            logger.info("then remove one of them and validate other works correct");
            int[] afterRemovalResult = jobPage.removeOneOfSelectedFiltersAndValidateResult();

            if (afterRemovalResult != null) {
                Assertions.assertEquals(afterRemovalResult[0], afterRemovalResult[1],
                        "After removal, filter count doesn't match results");
            }
            logger.info("Test4 completed successfully for filter group: {}", filterGroupName);
        } catch (Exception e) {
            logger.error("Test4 failed for filter group: {}", filterGroupName, e);
        }
    }
}
