package homework2.tests;

import homework2.JobPage;
import homework2.TestClass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.screenshots.ScreenshotExtension;
import java.util.List;

@ExtendWith(ScreenshotExtension.class)
public class JobPageTest extends TestClass2 {
    private static final Logger logger = LoggerFactory.getLogger(JobPageTest.class);
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
    public void filterJobsViaOneRandomFilterAndCheckPagination(String filterGroupName) {
        logger.info("Starting filterJobsViaOneRandomFilterAndCheckPagination test for filter group: {}", filterGroupName);
        //1
        try {
            logger.info("Click to view more button, get all checkboxes, filter via random filter");
            jobPage.clickToViewMoreButton(filterGroupName)
                    .getCheckboxes(filterGroupName)
                    .filterSectionViaRandomFilter();

            JobPage.FilterValidationResult result = jobPage.getFilterValidationResult();
            int filterCount = result.getFilterCount();
            int actualCount = result.getActualCount();
            boolean paginationVisible = result.isPaginationVisible();
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
            logger.info("filterJobsViaOneRandomFilterAndCheckPagination test completed succesfully for filter group : {}", filterGroupName);
        } catch (Exception e) {
            logger.error("filterJobsViaOneRandomFilterAndCheckPagination test failed for filter group: {}", filterGroupName, e);
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
    public void filterJobsViaTwoRandomFilters_RemoveOneOfThem(String filterGroupName) {
        logger.info("Starting filterJobsViaTwoRandomFilters_RemoveOneOfThem test for filter group: {}", filterGroupName);

        //2
        try {
            jobPage.clickToViewMoreButton(filterGroupName);
            jobPage.getCheckboxes(filterGroupName);
            logger.info("choose two filters , check they work correct together");
            List<Integer> result = jobPage.getFirstAndSecondFiltersSumCountAndResultCount(filterGroupName);
            int firstFilterCount = result.get(0);
            int secondFilterCount = result.get(1);
            int totalResults = result.get(2);
            logger.info("First filter count: {}, Second filter count: {}, Total result: {}",
                    firstFilterCount, secondFilterCount, totalResults);

            Assertions.assertTrue(totalResults <= firstFilterCount + secondFilterCount,
                    "Total result should be less than or equal to both filter counts sum");


            logger.info("then remove one of them and validate other works correct");
            JobPage.FilterValidationResult afterRemovalResult = jobPage.removeOneOfSelectedFiltersAndValidateResult();

            if (afterRemovalResult != null) {
                Assertions.assertEquals(afterRemovalResult.getFilterCount(), afterRemovalResult.getActualCount(),
                        "After removal, filter count doesn't match results");
            }
            logger.info("filterJobsViaTwoRandomFilters_RemoveOneOfThem test completed successfully for filter group: {}", filterGroupName);
        } catch (TimeoutException e) {
            logger.error("filterJobsViaTwoRandomFilters_RemoveOneOfThem test failed for filter group: {}", filterGroupName, e);
        }
    }
}
